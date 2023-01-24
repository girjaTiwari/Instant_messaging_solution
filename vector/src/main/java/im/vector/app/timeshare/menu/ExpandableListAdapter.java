package im.vector.app.timeshare.menu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;

import im.vector.app.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> _listDataChild;

    RotateAnimation r;
    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListChild) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this._listDataChild = expandableListChild;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild.get(this.expandableListTitle.get(groupPosition))
                .get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        // Getting child text
        final String childText = (String) getChild(groupPosition, childPosition);


        // Inflating child layout and setting textview
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_child,parent,false );
        }

        TextView child_text = (TextView) convertView.findViewById(R.id.child);

        child_text.setText(childText);
        if (isLastChild){
            if (groupPosition==1){
                child_text.setTextColor(Color.parseColor("#F44336"));
            }else {
                child_text.setTextColor(Color.parseColor("#808080"));
            }

        }else {
            child_text.setTextColor(Color.parseColor("#808080"));
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return this._listDataChild.get(this.expandableListTitle.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        // Getting header title
        String headerTitle = (String) getGroup(groupPosition);

        // Inflating header layout and setting text
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_header, parent,false);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.header);
        ImageView iv_arrow = convertView.findViewById(R.id.iv_arrow);

        header_text.setText(headerTitle);
        if (groupPosition==5){
            iv_arrow.setVisibility(View.INVISIBLE);
            header_text.setTextColor(Color.parseColor("#F44336"));
        }else {
            iv_arrow.setVisibility(View.VISIBLE);
            header_text.setTextColor(Color.parseColor("#090909"));
        }

        if (groupPosition==0){
            iv_arrow.setRotation(90);
        }else if (groupPosition==1){
            iv_arrow.setRotation(90);
        }else {
            iv_arrow.setRotation(0);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
