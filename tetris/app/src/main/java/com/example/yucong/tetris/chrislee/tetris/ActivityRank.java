package com.example.yucong.tetris.chrislee.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.yucong.tetris.R;


public class ActivityRank extends Activity {

    private RankDatabase mDatabase = null;
    private ListView mListView = null;

    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setTitle("排行榜");
        setContentView(R.layout.rank);
//		init();
    }

//	private void init()
//	{
//		// ..get databse
//		ListView mListView = (ListView)findViewById(R.id.rank_list);
//		
//		SimpleAdapter adapter = new SimpleCursorAdapter(this,
//				cur,
//				R.layout.list_item,
//				new String[]{"rank,scroe,name"},
//				new int[] {R.id.list_item_rank,R.id.list_item_score,R.list_item_name} );
//		mListView.setAdapter(adapter);
//	}

}
