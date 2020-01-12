package com.example.omartotangy.myapplication.PublicClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.omartotangy.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Omar Totangy
 * This class is used to create an expandable listview.
 *
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> headers;
    public HashMap<String, ArrayList<String>> children;
    private Context ctx;

    /**
     *
     * @param ctx context, pass in "this" or "getActivity"
     * @param children Hashmap of the children items and a string of the parent header.
     * @param headers A string arraylist of all the headers.
     */
    public ExpandableListViewAdapter(Context ctx, HashMap<String, ArrayList<String>> children, ArrayList<String> headers){
        this.headers = headers;
        this.children = children;
        this.ctx = ctx;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return children.get(headers.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headers.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return children.get(headers.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Inflates the listview when it is clicked to get a groupview.
     * @param groupPosition Where the expandable listview is clicked
     * @param isExpanded If it is expanded or not
     * @param convertView View to set text, and to expand when the list is not expanded.
     * @param parent ViewGroup
     * @return convertView
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = "" + this.getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_listview_parent, null);
        }
        TextView textView = convertView.findViewById(R.id.header);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }

    /**
     * Inflates the children items
     * @param groupPosition the position where it is clicked
     * @param childPosition the position of the child
     * @param isLastChild checks to see if it is the last child or not
     * @param convertView View to set text, and to expand when the list is not expanded.
     * @param parent ViewGroup
     * @return convertView
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = "" + this.getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_listview_child, null);

        }
        TextView textView = convertView.findViewById(R.id.headerchild);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }

    /**
     * Checks if the child is selectable
     * @param i groupPosition
     * @param i1 childPosition
     * @return boolean
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
