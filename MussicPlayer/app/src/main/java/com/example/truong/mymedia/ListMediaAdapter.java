package com.example.truong.mymedia;

/**
 * Created by truong on 14/03/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
public class ListMediaAdapter extends  BaseAdapter{




        private List<Medialistview> listData;
        private LayoutInflater layoutInflater;
        private Context context;

        public ListMediaAdapter(Context aContext,  List<Medialistview> listData) {
            this.context = aContext;
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }


    @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listviewmedia, null);
                holder = new ViewHolder();
                holder.Im = (ImageView) convertView.findViewById(R.id.imageView_list);
                holder.Name = (TextView) convertView.findViewById(R.id.textView_name_mussic);
                holder.Path = (TextView) convertView.findViewById(R.id.textView_path);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Medialistview medialistview = this.listData.get(position);
            holder.Name.setText(medialistview.getName());
            holder.Path.setText("Path: " + medialistview.getPath());

            int imageId = this.getMipmapResIdByName(String.valueOf(medialistview.getIm()));

            holder.Im.setImageResource(imageId);
            return convertView;
        }

        // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
        public int getMipmapResIdByName(String resName)  {
            String pkgName = context.getPackageName();

            // Trả về 0 nếu không tìm thấy.
            int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
            //Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
            return resID;
        }

        static class ViewHolder {
            ImageView Im;
            TextView Name;
            TextView Path;
        }


}
