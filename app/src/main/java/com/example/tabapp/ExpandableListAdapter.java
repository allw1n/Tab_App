package com.example.tabapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context _context;
    private final List<String> _listMenuHeader;
    private final HashMap<String, List<String>> _listMenuChild;

    public ExpandableListAdapter(Context context, List<String> listMenuHeader,
                                 HashMap<String, List<String>> listMenuChild) {
        _context = context;
        _listMenuHeader = listMenuHeader;
        _listMenuChild = listMenuChild;
    }

    @Override
    public int getGroupCount() {
        return _listMenuHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listMenuHeader.get(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).toString();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, parent, false);
        }

        TextView textListHeader = convertView.findViewById(R.id.textListHeader);
        textListHeader.setTypeface(null, Typeface.BOLD);
        textListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(_listMenuChild.get(_listMenuHeader.get(groupPosition))).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(_listMenuChild.get(_listMenuHeader.get(groupPosition))).get(childPosition);
    }

    @Override
    public View getChildView(int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childTitle = getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_child, parent, false);
        }

        TextView textListChild = convertView.findViewById(R.id.textListChild);
        textListChild.setText(childTitle);
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
