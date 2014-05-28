package com.iihm.plasticity.appslist.adapter;

import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.iihm.plasticity.appslist.activity.R;

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
		holder.apkName.setText(renameApp(appName));

		return convertView;
	}
	
	/**
	 * Rename the app if the name if too long (20 char max)
	 * @param name
	 * @return
	 */
	private String renameApp(String name) 
	{
		int nbCharMax = 20;
		String newName = null;
		if (name.length() > nbCharMax)
		{
			String[] split = null;
			int cptChar = 0;
			newName = new String();
			split = name.split("\\s+");
			for (int i = 0; i < split.length - 1; i++)
			{
				cptChar += split[i].length();
				if (newName.isEmpty())
					newName = split[i];
				else if (cptChar < nbCharMax) {
					newName = newName.concat(" " + split[i]);
				}
			}
		}
		else
			newName = name;
		assert(newName != null);
		return newName;
	}
}