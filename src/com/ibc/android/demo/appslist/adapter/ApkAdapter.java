package com.ibc.android.demo.appslist.adapter;

import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ibc.android.demo.appslist.activity.R;

public class ApkAdapter extends BaseAdapter {

	List<PackageInfo> packageList;
	Activity context;
	PackageManager packageManager;

	public ApkAdapter(Activity context, List<PackageInfo> packageList,
			PackageManager packageManager) {
		super();
		this.context = context;
		this.packageList = packageList;
		this.packageManager = packageManager;
	}

	private class ViewHolder {
		TextView apkName;
	}

	public int getCount() {
		return packageList.size();
	}

	public Object getItem(int position) {
		return packageList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.apklist_item, null);
			holder = new ViewHolder();

			holder.apkName = (TextView) convertView.findViewById(R.id.appname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PackageInfo packageInfo = (PackageInfo) getItem(position);
		Drawable appIcon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
		String appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
		appIcon.setBounds(0, 0, (int)context.getResources().getDimension(R.dimen.icon_width),
				(int)context.getResources().getDimension(R.dimen.icon_height));
		holder.apkName.setCompoundDrawables(null, appIcon, null, null);
		holder.apkName.setCompoundDrawablePadding(3);
//		holder.apkName.setLayoutParams(new GridView.LayoutParams(
//				(int)context.getResources().getDimension(R.dimen.width),
//				(int)context.getResources().getDimension(R.dimen.height)));
		holder.apkName.setText(appName);

		return convertView;
	}
}