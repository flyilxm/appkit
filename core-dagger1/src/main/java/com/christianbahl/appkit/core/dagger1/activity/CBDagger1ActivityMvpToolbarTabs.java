/*
 * Copyright 2015 Christian Bahl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.christianbahl.appkit.core.dagger1.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import com.christianbahl.appkit.core.dagger1.R;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * <p>
 * An activity which uses the Model-View-Presenter architecture and Dagger 1 for dependency
 * injection.<br/>
 * It also adds a {@link Toolbar} on top and has a {@link ViewPager} with {@link
 * TabLayout}.
 * </p>
 *
 * <p>
 * The layout has to contain a view with id <code>R.layout.content_view</code> which must be of
 * type {@link ViewPager}. You also have to provide a view with id <code>R.layout.tabs</code> of
 * type {@link TabLayout}.
 * </p>
 *
 * <p>
 * The standard layout implements all necessary views. You can override the default layout in
 * {@link #getLayoutRes()}. But be careful, you have to provide the necessary views!
 * </p>
 *
 * <p>
 * There a two functions to customize the {@link ViewPager}<br/>
 *
 * * getPageMargin(): sets the margin between the pages<br/>
 * * getViewPagerDividerDrawable(): sets divider {@link Drawable} between the pages
 * </p>
 *
 * @author Christian Bahl
 * @see CBDagger1ActivityMvpToolbar
 */
public abstract class CBDagger1ActivityMvpToolbarTabs<M, V extends MvpLceView<M>, P extends MvpPresenter<V>, A extends PagerAdapter>
    extends CBDagger1ActivityMvpToolbar<ViewPager, M, V, P> {

  protected TabLayout tabs;
  protected A adapter;

  @Override protected void onMvpViewCreated() {
    super.onMvpViewCreated();

    tabs = (TabLayout) findViewById(R.id.tabs);
    if (tabs == null) {
      throw new NullPointerException(
          "No tabs found. Did you forget to add it to your layout file with the id R.id.tabs?");
    }

    adapter = createAdapter();
    if (adapter == null) {
      throw new NullPointerException(
          "No adapter found. Did you forget to create own in createAdapter()?");
    }

    contentView.setAdapter(adapter);
    tabs.setupWithViewPager(contentView);

    int margin = Math.max(getPageMargin(), 0);

    if (margin > 0) {
      contentView.setPageMargin(margin);
      contentView.setPageMarginDrawable(getViewPagerDividerDrawable());
    }
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.cb_activity_mvp_toolbar_tabs;
  }

  /**
   * The margin between the pages in the {@link ViewPager}
   *
   * @return margin between pages in {@link ViewPager}
   */
  protected int getPageMargin() {
    return 20;
  }

  /**
   * The {@link Drawable} which will be displayed between the pages in
   * the {@link ViewPager} if `{@link #getPageMargin()} ] > 0`
   *
   * @return divider {@link Drawable} for the {@link ViewPager}
   */
  protected Integer getViewPagerDividerDrawable() {
    return R.drawable.cb_viewpager_divider;
  }

  /**
   * Creates the {@link A} for the {@link ViewPager}.<br/>
   * Called in {@link #onCreate(Bundle)}
   *
   * @return {@link A}
   */
  protected abstract A createAdapter();
}
