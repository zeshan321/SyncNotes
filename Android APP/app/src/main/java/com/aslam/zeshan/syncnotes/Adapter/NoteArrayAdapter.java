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
import com.aslam.zeshan.syncnotes.Note;
import com.aslam.zeshan.syncnotes.R;
import com.aslam.zeshan.syncnotes.Util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class NoteArrayAdapter extends ArrayAdapter<Note> {

    List<Note> emailsList = new ArrayList<Note>();
    List<Note> etList = new ArrayList<Note>();

    String last;
    Context con;


    public NoteArrayAdapter(Context con, int resource) {
        super(con, resource);

        this.con = con;
    }

    @Override
    public void add(Note object) {
        emailsList.add(object);
        super.add(object);
    }

    @Override
    public void clear() {
        this.emailsList.clear();

        super.clear();
    }

    @Override
    public void remove(Note object) {
        emailsList.remove(object);
        super.remove(object);
    }

    public void add(int i, Note object) {
        emailsList.add(i, object);
    }

    public void set(int i, Note object) {
        emailsList.set(i, object);
    }

    public int getCount() {
        return this.emailsList.size();
    }

    public Note getItem(int index) {
        return this.emailsList.get(index);
    }

    public Note getByID(String value) {
        Note note = null;
        for (Note note1 : emailsList) {
            if (note1.getID().equals(value)) {
                note = note1;
                break;
            }
        }
        return note;
    }

    public void removeNote(int index) {
        emailsList.remove(index);
    }

    public View getView(int position, View row, ViewGroup parent) {
        Note note = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.note_list_view, parent, false);

        TextView title = (TextView) row.findViewById(R.id.listTitle);
        TextView body = (TextView) row.findViewById(R.id.listPreview);
        ImageView image = (ImageView) row.findViewById(R.id.selected);

        title.setText(note.getTitle());
        body.setText(note.getBody());

        ColorGenerator generator = ColorGenerator.DEFAULT;

        String s = null;
        try {
            s = String.valueOf(note.getTitle().charAt(0)).toUpperCase();
        } catch(StringIndexOutOfBoundsException e) {
            s = "*";
        }

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
            ArrayList<Note> tempList = new ArrayList<>();

            for (Note Note : emailsList) {
                if (!etList.contains(Note)) {
                    etList.add(Note);
                }
            }

            if (!last.equals(constraint.toString())) {
                emailsList = etList;
            }

            if (new StringUtil().checkString(constraint.toString())) {
                for (Note Note : emailsList) {

                    String name = Note.getTitle().toLowerCase();
                    String email = Note.getBody().toLowerCase();

                    if (name.contains(constraint.toString().toLowerCase()) || email.contains(constraint.toString().toLowerCase())) {
                        tempList.add(Note);
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
            emailsList = (ArrayList<Note>) results.values;
            notifyDataSetChanged();
        }
    };

    public void restoreList() {
        if (etList != null)
            emailsList = etList;
    }
}