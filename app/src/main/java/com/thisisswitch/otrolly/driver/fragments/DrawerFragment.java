package com.thisisswitch.otrolly.driver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.NavigationDrawerAdapter;
import com.thisisswitch.otrolly.driver.models.NavigationDrawerItem;
import com.thisisswitch.otrolly.driver.models.UserProfile;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;
import com.thisisswitch.otrolly.driver.utils.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Raja Gopal Reddy P
 */
public class DrawerFragment extends Fragment {
    private static String TAG = DrawerFragment.class.getSimpleName();
    @Bind(R.id.userIcon)
    ImageView userIcon;
    @Bind(R.id.username_textView)
    TextView usernameTextView;
    @Bind(R.id.vehicle_id_textView)
    TextView vehicleIdTextView;
    @Bind(R.id.nav_header_container)
    RelativeLayout navHeaderContainer;
    @Bind(R.id.drawerList)
    RecyclerView drawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static int[] icons = null;
    private FragmentDrawerListener drawerListener;

    public DrawerFragment() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavigationDrawerItem> getData() {
        List<NavigationDrawerItem> data = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setIcon(icons[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        icons = new int[titles.length];
        icons[0] = R.drawable._0000_home_normal;
        icons[1] = R.drawable._0021_trip_history;
        icons[2] = R.drawable._0016_track_orders;
        icons[3] = R.drawable._0017_logout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.bind(this, layout);

        setRecycleAdapter();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UserProfile userProfile = AppPreferences.getInstance().getUser();
        if (userProfile != null) {
            usernameTextView.setText(userProfile.getFullName());
            vehicleIdTextView.setText(userProfile.getVehicleId());

            if (userProfile.getUrl() != null) {
                String imageUrl = RestAPIRequest.BASE_URL_IP + "/api" + userProfile.getUrl();

                Picasso.with(getActivity())
                        .load(imageUrl)
                        .transform(new CircleTransform())
                        .placeholder(R.drawable._0005_driver_name)
                        .error(R.drawable._0005_driver_name)
                        .into(userIcon);
            }
        }
    }

    private void setRecycleAdapter() {
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        drawerList.setAdapter(adapter);
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        drawerList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), drawerList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
                changeMenuStyle();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void changeMenuStyle() {
        LinearLayout layout1 = (LinearLayout) drawerList.getChildAt(0);
        layout1.getChildAt(0).setBackgroundResource(icons[0]);
        layout1.setBackgroundColor(getResources().getColor(R.color.nav_selected_background));
        ((TextView) layout1.getChildAt(1)).setTextColor(getResources().getColor(R.color.main));
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

}
