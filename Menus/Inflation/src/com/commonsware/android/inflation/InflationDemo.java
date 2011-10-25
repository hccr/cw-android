/***
  Copyright (c) 2008-2011 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.commonsware.android.inflation;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class InflationDemo extends ListActivity {
  private static final String[] items={"lorem", "ipsum", "dolor",
          "sit", "amet", "consectetuer", "adipiscing", "elit",
          "morbi", "vel", "ligula", "vitae", "arcu", "aliquet",
          "mollis", "etiam", "vel", "erat", "placerat", "ante",
          "porttitor", "sodales", "pellentesque", "augue", "purus"};
  private ArrayList<String> words=null;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    
    initAdapter();
    registerForContextMenu(getListView());
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    new MenuInflater(this).inflate(R.menu.option, menu);

    return(super.onCreateOptionsMenu(menu));
  }
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
    new MenuInflater(this).inflate(R.menu.context, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.add:
        add();
        return(true);
    
      case R.id.reset:
        initAdapter();
        return(true);
    }
    
    return(super.onOptionsItemSelected(item));
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterView.AdapterContextMenuInfo info=
      (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    ArrayAdapter<String> adapter=(ArrayAdapter<String>)getListAdapter();

    switch (item.getItemId()) {
      case R.id.cap:
        String word=words.get(info.position);
        
        word=word.toUpperCase();
        
        adapter.remove(words.get(info.position));
        adapter.insert(word, info.position);
        
        return(true);
    
      case R.id.remove:
        adapter.remove(words.get(info.position));
        
        return(true);
    }
    
    return(super.onContextItemSelected(item));
  }
  
  private void initAdapter() {
    words=new ArrayList<String>();
    
    for (String s : items) {
      words.add(s);
    }
    
    setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, words));
  }
  
  private void add() {
    final View addView=getLayoutInflater().inflate(R.layout.add, null);
    
    new AlertDialog.Builder(this)
      .setTitle("Add a Word")
      .setView(addView)
      .setPositiveButton("OK",
                          new DialogInterface.OnClickListener() {
        @SuppressWarnings("unchecked")
        public void onClick(DialogInterface dialog,
                              int whichButton) {
          ArrayAdapter<String> adapter=(ArrayAdapter<String>)getListAdapter();
          EditText title=(EditText)addView.findViewById(R.id.title);
          
          adapter.add(title.getText().toString());
        }
      })
      .setNegativeButton("Cancel", null)
      .show();
  }
}