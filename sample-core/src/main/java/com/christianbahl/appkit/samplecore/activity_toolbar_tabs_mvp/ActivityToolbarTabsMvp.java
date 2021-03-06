package com.christianbahl.appkit.samplecore.activity_toolbar_tabs_mvp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarTabs;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import java.util.List;

/**
 * @author Christian Bahl
 */
public class ActivityToolbarTabsMvp extends
    CBActivityMvpToolbarTabs<List<String>, MvpLceView<List<String>>, ActivityToolbarTapsMvpPresenter, ActivityToolbarTabsMvpAdapter> {

  public static Intent getStartIntent(Context context) {
    return new Intent(context, ActivityToolbarTabsMvp.class);
  }

  @Override protected ActivityToolbarTabsMvpAdapter createAdapter() {
    return new ActivityToolbarTabsMvpAdapter(getSupportFragmentManager());
  }

  @NonNull @Override public ActivityToolbarTapsMvpPresenter createPresenter() {
    return new ActivityToolbarTapsMvpPresenter();
  }

  @Override public void setData(List<String> data) {
    adapter.setItems(data);
    adapter.notifyDataSetChanged();
    tabs.setTabsFromPagerAdapter(adapter);
  }

  @Override public void loadData(boolean pullToRefresh) {
    presenter.loadData(pullToRefresh);
  }
}
