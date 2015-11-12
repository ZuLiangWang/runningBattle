package wamcs.runningbattle.mapDistance;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

import wamcs.runningbattle.R;

/**
 * Created by zuliangwang on 15/10/24.
 */
public class MapApplication extends Application {
    public LocationClient mLocationClient = null;
    public BDLocationListener listener = null;
    public double distance=0;
    public MapPoint lastMapPoint;
    public MapPoint curMapPoint;




    @Override
    public void onCreate() {
        super.onCreate();
        initLocationDetector();
        this.startService(new Intent(this, TimeService.class));
        AVOSCloud.initialize(this,getResources().getString(R.string.app_id),getResources().getString(R.string.app_key));
    }

    private void initLocationOptions(){
        lastMapPoint = new MapPoint();
        curMapPoint = new MapPoint();
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public double getDistance(){
        return distance;
    }

    public void resetDistance(){
        distance = 0;
    }

    private void initLocationDetector(){
        listener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //Receive Location
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(bdLocation.getTime());
                sb.append("\nerror code : ");
                sb.append(bdLocation.getLocType());
                sb.append("\nlatitude : ");//纬度
                sb.append(bdLocation.getLatitude());
                sb.append("\nlontitude : ");//经度
                sb.append(bdLocation.getLongitude());
                sb.append("\nradius : ");
                sb.append(bdLocation.getRadius());
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(bdLocation.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(bdLocation.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(bdLocation.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(bdLocation.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(bdLocation.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");


                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(bdLocation.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(bdLocation.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
                List<Poi> list = bdLocation.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("BaiduLocationApiDem", sb.toString());


                if (curMapPoint.getLatitude() != -1 || curMapPoint.getLongtitude() !=-1) {
                    lastMapPoint.setLongtitude(curMapPoint.getLongtitude());
                    lastMapPoint.setLatitude(curMapPoint.getLatitude());
                }
                curMapPoint.setLongtitude(bdLocation.getLongitude());
                curMapPoint.setLatitude(bdLocation.getLatitude());



                if (lastMapPoint.getLatitude()!=-1)
                    distance += DistanceMaster.GetShortDistance(lastMapPoint.getLongtitude(), lastMapPoint.getLatitude(), curMapPoint.getLongtitude(), curMapPoint.getLatitude());

                Log.d("ddddd","lastLatitude="+lastMapPoint.getLatitude()+"   lastLongtitude" +lastMapPoint.getLongtitude());
                Log.d("eeeee","curLatitude="+curMapPoint.getLatitude()+"curLongtitude="+curMapPoint.getLongtitude());
                Log.d("Distance",""+distance);
            }
        };
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(listener);
        initLocationOptions();
        mLocationClient.start();
    }
}
