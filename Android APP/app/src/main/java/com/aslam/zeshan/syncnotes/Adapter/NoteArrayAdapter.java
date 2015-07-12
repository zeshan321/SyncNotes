package com.aslam.zeshan.syncnotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.aslam.zeshan.syncnotes.NoteObject;
import com.aslam.zeshan.syncnotes.R;
import com.aslam.zeshan.syncnotes.Util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class NoteArrayAdapter extends ArrayAdapter<NoteObject> {

    List<NoteObject> emailsList = new ArrayList<NoteObject>();
    List<NoteObject> etList = new ArrayList<NoteObject>();

    String last;
    Context con;


    public NoteArrayAdapter(Context con, int resource) {
        super(con, resource);

        this.con = con;
    }

    @Override
    public void add(NoteObject object) {
        emailsList.add(object);
        super.add(object);
    }

    @Override
    public void clear() {
        this.emailsList.clear();

        super.clear();
    }

    @Override
    public void remove(NoteObject object) {
        emailsList.remove(object);
        super.remove(object);
    }

    public void add(int i, NoteObject object) {
        emailsList.add(i, object);
    }

    public void set(int i, NoteObject object) {
        emailsList.set(i, object);
    }

    public int getCount() {
        return this.emailsList.size();
    }

    public NoteObject getItem(int index) {
        return this.emailsList.get(index);
    }

    public void removeEmail(int index) {
        emailsList.remove(index);
    }

    public View getView(int position, View row, ViewGroup parent) {
        NoteObject noteObject = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.note_list_view, parent, false);

        TextView title = (TextView) row.findViewById(R.id.listTitle);
        TextView body = (TextView) row.findViewById(R.id.listPreview);
        ImageView image = (ImageView) row.findViewById(R.id.selected);

        title.setText(noteObject.getTitle());
        body.setText(noteObject.getBody());

        ColorGenerator generator = ColorGenerator.DEFAULT;

        String s = String.valueOf(noteObject.getTitle().charAt(0)).toUpperCase();
        int color2 = generator.getColor(s);

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(97)
                .height(97)
                .endConfig()
                .buildRect(s, color2);

        image.setImageDrawable(drawable);

        return row;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (last == null) {
                last = constraint.toString();
            }

            FilterResults filterResults = new FilterResults();
            ArrayList<NoteObject> tempList = new ArrayList<>();

            for (NoteObject NoteObject: emailsList) {
                if (!etList.contains(NoteObject)) {
                    etList.add(NoteObject);
                }
            }

            if (!last.equals(constraint.toString())) {
                emailsList = etList;
            }

            if (new StringUtil().checkString(constraint.toString())) {
                for (NoteObject NoteObject : emailsList) {

                    String name = NoteObject.getTitle().toLowerCase();
                    String email = NoteObject.getBody().toLowerCase();

                    if (name.contains(constraint.toString().toLowerCase()) || email.contains(constraint.toString().toLowerCase())) {
                        tempList.add(NoteObject);
                    }
                }
            } else {
                filterResults.values = emailsList;
                filterResults.count = emailsList.size();
                last = constraint.toString();

                return filterResults;
            }

            filterResults.values = tempList;
            filterResults.count = tempList.size();
            last = constraint.toString();

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            emailsList = (ArrayList<NoteObject>) results.values;
            notifyDataSetChanged();
        }
    };

    public void restoreList() {
        if (etList != null)
            emailsList = etList;
    }
}