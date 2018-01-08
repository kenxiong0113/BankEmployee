package com.ruan.bankemployee.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.User;
import com.ruan.bankemployee.other.BaseConstants;
import com.ruan.bankemployee.util.DrawMarkerUtil;
import com.ruan.overlay.PoiOverlay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * @author by ruan 2018/1/4
 */
public class BankLocationActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerClickListener{
    /**
     * 声明AMapLocationClient类对象
     */
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption;
    protected MyLocationStyle myLocationStyle;
    protected UiSettings uiSettings;
    protected PoiSearch poiSearch;
    protected double lat,lon;
    @BindView(R.id.map_view)
    protected MapView mapView;
    protected AMap aMap;
    protected String cityCode,poiCode = "160100";
    protected Message message = new Message();
    protected View viewBank;
    protected DrawMarkerUtil markerUtil;
    protected String title;
    protected Context context;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x0001:
                    searchPoi();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bank_location;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("银行定位");
        ButterKnife.bind(this);
        checkThePermissions();
        context = getApplicationContext();
        mapView.onCreate(savedInstanceState);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initAMap();

        setTopLeftButton(R.drawable.ic_return, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    /**
     * 实现定位蓝点
     */
    protected void locateTheBlueDot(){
        myLocationStyle = new MyLocationStyle();
        //定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW) ;
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        // 设置默认定位按钮是否显示，非必需设置。
        uiSettings.setMyLocationButtonEnabled(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.
                decodeResource(getResources(),R.mipmap.marker36)));
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.anchor(0.5f,0.5f);
//        设置精度圈，精度边框颜色
        myLocationStyle.strokeColor(getResources().getColor(R.color.transparency));
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparency));
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }
    /**
     * 初始化AMap
     */
    protected void initAMap(){
        if (aMap == null){
            aMap = mapView.getMap();
        }
        uiSettings = aMap.getUiSettings();
        markerUtil = new DrawMarkerUtil(aMap);
        // 设置点击marker事件监听器
        aMap.setOnMarkerClickListener(this);
        setUpMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
    /**
     * 检查是否获取定位权限
     */
    protected void checkThePermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CHANGE_WIFI_STATE}, 3);
    }
    /**
     * 异步获取定位结果
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //解析定位结果
                    // 设置当前地图显示为当前位置
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    cityCode = amapLocation.getCityCode();
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 18f));
                    locateTheBlueDot();
                    message.what = 0x0001;
                    handler.sendMessage(message);
                }else {
                    Log.e("BankLocationActivity", amapLocation.getErrorCode() + amapLocation.getErrorInfo());
                }
            }
        }
    };


    /**
     * 配置定位参数
     */
    protected void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(8000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    /**
     * 周边POI搜索
     */
    protected void searchPoi(){
        PoiSearch.Query query = new PoiSearch.Query("",poiCode,cityCode);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        //设置周边搜索的中心点以及半径
        // TODO: 2018/1/5 半径暂时设置8公里
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat,
                lon), 8000));
}
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        // TODO: 2017/12/25 在地图上面显示兴趣点
        Log.e("BankLocationActivity", "i:" + i);
        if (i == BaseConstants.CODE_MAP_SUCCESS){
            List<PoiItem> poiItems = poiResult.getPois();
            if (poiItems != null && poiItems.size() > 0) {
                aMap.clear();
                PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                poiOverlay.removeFromMap();
                poiOverlay.zoomToSpan();
            }
            for (int j = 0; j < poiResult.getPois().size(); j++) {
                viewBank = View.inflate(this,R.layout.view_bank, null);
                Bitmap bitmap = markerUtil.convertViewToBitmap(viewBank);
                markerUtil.drawMarkerOnMap(new LatLng(poiResult.getPois().get(j).getLatLonPoint().getLatitude(),
                                poiResult.getPois().get(j).getLatLonPoint().getLongitude()),
                        bitmap, poiResult.getPois().get(j).getTitle());
            }

        }else {
            Log.e("AtmFragment", "i:" + i);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final User user = BmobUser.getCurrentUser(User.class);
        final String bank = user.getBankName();
        title = marker.getTitle();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("身份认证");
        dialog.setMessage("认证"+title+"职员的身份信息");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (bank != null && !bank.equals(title)){
                    Toast.makeText(BankLocationActivity.this,
                            "已在" + bank + "申请认证身份信息", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    if (!user.isCommit()){
                        //银行职员未提交信息
                        Intent intent = new Intent(context,AuthenticationActivity.class);
                        intent.putExtra("bank",title);
                        startActivity(intent);
                    }else{
                      fetchUserInfo();
                    }
                }
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BankLocationActivity.this, "取消身份认证", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        return false;
    }

    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     * Synchronize local caching
     * @see cn.bmob.v3.helper.ErrorCode#E9024S
     */
    public  void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("1111","Newest UserInfo is " + s);
                    try {
                        JSONObject object = new JSONObject(s);
                        //获取待审信息 true 表示已审核 false 表示未审核
                        boolean isPendingTrial = object.getBoolean("pendingTrial");
                        //银行职员已提交信息
                        if (isPendingTrial){
                            //已审核，无论审核结果怎样，跳转到审核结果的活动界面
                            startActivity(new Intent(context,AuditResultActivity.class));
                        }else {
                            //待审核
                            Intent intent = new Intent(context,ToAuditActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.e("WelcomeActivity", "e.getErrorCode():" + e.getErrorCode());
                }
            }
        });
    }
}
