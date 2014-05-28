package com.iihm.plasticity.appslist.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.iihm.plasticity.appslist.adapter.ApkAdapter;
import com.iihm.plasticity.appslist.app.AppData;

public class AppListActivity extends Activity 
implements OnItemClickListener 
{

	PackageManager packageManager;
	ListView apkList;
	TextView app1;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		packageManager = getPackageManager();
		// Liste des packages installees
		List<PackageInfo> packList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		// Liste des packages filtrees
		List<PackageInfo> packListFilt = new ArrayList<PackageInfo>();

		//		ArrayList<AppInfo> res = new ArrayList<AppInfo>();
		GridView grid = (GridView) findViewById(R.id.applist);
		String appName = null;
		Drawable appIcon = null;
		for(PackageInfo pI : packList) 
		{
			// On filtre les packages
			if(packageManager.getLaunchIntentForPackage(pI.packageName) != null) 
			{
				// On recup les noms et icones de chaque app
//				appName = pI.applicationInfo.loadLabel(getPackageManager()).toString();
//				appIcon = pI.applicationInfo.loadIcon(getPackageManager());
//				//				res.add(newInfo);
//				app1 = new TextView(this);
//				app1.setText(renameApp(appName));
//				// Definit les icones a mettre en left/top/right/bot du texte
//				app1.setCompoundDrawablesWithIntrinsicBounds(null, appIcon, null, null);
				packListFilt.add(pI);
			}
		}
		grid.setAdapter(new ApkAdapter(this, packListFilt, packageManager));

		grid.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long row)
	{
		PackageInfo packageInfo = (PackageInfo) parent
				.getItemAtPosition(position);
		AppData appData = (AppData) getApplicationContext();
		appData.setPackageInfo(packageInfo);

		Intent appInfo = new Intent(getApplicationContext(), ApkInfo.class);
		startActivity(appInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		//getMenuInflater().inflate(R.menu.activity_apk_list, menu);
		return true;
	}
}
