package com.ibc.android.demo.appslist.activity;

import java.util.ArrayList;
import java.util.List;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import com.ibc.android.demo.appslist.adapter.ApkAdapter;
import com.ibc.android.demo.appslist.app.AppData;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ApkListActivity extends Activity 
implements OnItemClickListener {

	PackageManager packageManager;
	ListView apkList;
	Button app1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		packageManager = getPackageManager();
		// Liste des apps installees
		List<PackageInfo> appList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		// Liste des apps filtrees
		List<PackageInfo> appList1 = new ArrayList<PackageInfo>();

		ArrayList<AppInfo> res = new ArrayList<AppInfo>();
		GridView grid = (GridView) findViewById(R.id.applist);

		
//		WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
//		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
//		this.setBackground(wallpaperDrawable);
		
		
		/*To filter out System apps*/
		for(PackageInfo pi : appList) {
			if(packageManager.getLaunchIntentForPackage(pi.packageName) != null && pi.applicationInfo.loadLabel(getPackageManager()).toString().length() < 25) {

				// On recup les noms et icones de chaque app
				AppInfo newInfo = new AppInfo();
				newInfo.appname = pi.applicationInfo.loadLabel(getPackageManager()).toString();
				newInfo.icon = pi.applicationInfo.loadIcon(getPackageManager());
				res.add(newInfo);
				app1 = new Button(this);
				app1.setText(newInfo.appname);
				// Definit les icones a mettre en left/top/right/bot du texte du bouton
				app1.setCompoundDrawablesWithIntrinsicBounds(null, newInfo.icon, null, null);
				appList1.add(pi);
			}
		}
		grid.setAdapter(new ApkAdapter(this, appList1, packageManager));

		grid.setOnItemClickListener(this);
		
	}

	/**
	 * Return whether the given PackgeInfo represents a system package or not.
	 * User-installed packages (Market or otherwise) should not be denoted as
	 * system packages.
	 * 
	 * @param pkgInfo
	 * @return boolean
	 */
//	private boolean isSystemPackage(ApplicationInfo pkgInfo) {
//		return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
//	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long row) {
		PackageInfo packageInfo = (PackageInfo) parent
				.getItemAtPosition(position);
		AppData appData = (AppData) getApplicationContext();
		appData.setPackageInfo(packageInfo);

		Intent appInfo = new Intent(getApplicationContext(), ApkInfo.class);
		startActivity(appInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_apk_list, menu);
		return true;
	}
}

class AppInfo {
	String appname = "";
	String pname = "";
	String versionName = "";
	int versionCode = 0;
	Drawable icon;

}