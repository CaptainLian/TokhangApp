package dlsu.wirtec.tokhangapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.database.CursorRecyclerViewAdapter;
import dlsu.wirtec.tokhangapp.database.Score;

/**
 * Created by lyssa on 02/03/2017.
 */

public class ScoreAdapter extends CursorRecyclerViewAdapter<ScoreAdapter.ScoreViewHolder> {


    public ScoreAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder v, Cursor c) {
        final int id = c.getInt(c.getColumnIndex(Score.COLUMN_ID));
        final String name = c.getString(c.getColumnIndex(Score.COLUMN_NAME));
        final int score = c.getInt(c.getColumnIndex(Score.COLUMN_ID));

        v.tvNumber.setText(String.valueOf(id));
        v.tvName.setText(name);
        v.tvScore.setText(String.valueOf(score));
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_player, parent, false);
        return new ScoreViewHolder(v);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvNumber, tvName, tvScore;

        public ScoreViewHolder(View itemView) {
            super(itemView);

            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
        }// constructor ScoreViewHolder
    }// class ScoreViewHolder
}// class ScoreAdapter
