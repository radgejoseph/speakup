package com.speakup.dfs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPagerAdapter extends PagerAdapter {

    Context ctx;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView image_tutorial = view.findViewById(R.id.image_tutorial);
        ImageView ind1 = view.findViewById(R.id.ind1);
        ImageView ind2 = view.findViewById(R.id.ind2);
        ImageView ind3 = view.findViewById(R.id.ind3);
        ImageView ind4 = view.findViewById(R.id.ind4);
        ImageView ind5 = view.findViewById(R.id.ind5);
        ImageView ind6 = view.findViewById(R.id.ind6);
        ImageView ind7 = view.findViewById(R.id.ind7);
        ImageView ind8 = view.findViewById(R.id.ind8);
        ImageView ind9 = view.findViewById(R.id.ind9);
        ImageView ind10 = view.findViewById(R.id.ind10);

        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);

        ImageView next = view.findViewById(R.id.next);
        ImageView back = view.findViewById(R.id.back);
        Button get_started_button = view.findViewById(R.id.get_stated_button);
        get_started_button.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, SplashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });
        next.setOnClickListener(v -> SlideActivity.viewPager.setCurrentItem(position+1));
        back.setOnClickListener(v -> SlideActivity.viewPager.setCurrentItem(position-1));

        switch (position)
        {
            case 0:
                image_tutorial.setImageResource(R.drawable.screenshot_1);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("Welcome to SpeakUp");
                description.setText("This walk-through guide will only appear once. Swipe left or click the arrow at the right to see how the app works.");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
            case 1:
                image_tutorial.setImageResource(R.drawable.screenshot_2);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("Login/View Reviews and Register");
                description.setText("Here you can click on any items(vehicle plate) listed and view the reviews on them. " +
                        "You can search and sort them alphabetically or by their average ratings. You can also Login or register through the button.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:
                image_tutorial.setImageResource(R.drawable.screenshot_3);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("The Home");
                description.setText("Here you can click the different PUV's to view the next page that contains the plate number of each group of PUV. " +
                        "You can also access here the navigation menu by swiping right or by clicking the menu button (3 line) at the upper-left corner of the screen.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 3:
                image_tutorial.setImageResource(R.drawable.screenshot_4);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.selected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("The Navigation Menu");
                description.setText("By swiping right or by clicking the menu button (3 line) at the upper-left corner of the screen you will be presented with this menu.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 4:
                image_tutorial.setImageResource(R.drawable.screenshot_5);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.selected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("My Profile");
                description.setText("In My Profile you can view all your details and your account status. " +
                        "You can also edit your profile by clicking the pen button at the upper-right of the screen and once your done you can click again the save icon.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 5:
                image_tutorial.setImageResource(R.drawable.screenshot_6);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.selected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("My Ratings & Complaints");
                description.setText("In this page you will be presented with all the ratings you submitted and also your complaints and it's status.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 6:
                image_tutorial.setImageResource(R.drawable.screenshot_7);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.selected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("List of Plate Numbers");
                description.setText("After clicking what PUV to rate, you will be presented with this clickable plate number list. " +
                        "You can search and sort them alphabetically or by their average ratings.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 7:
                image_tutorial.setImageResource(R.drawable.screenshot_8);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.selected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("Reviews on Plate Number");
                description.setText("By selecting a plate number on the list, " +
                        "you will be presented with this list of reviews sorted from latest to oldest submitted by other users. " +
                        "You can rate your selected plate by clicking the PROCEED button");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 8:
                image_tutorial.setImageResource(R.drawable.screenshot_9);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.selected);
                ind10.setImageResource(R.drawable.unselected);

                title.setText("Send Ratings to Your Selected Plate");
                description.setText("You can rate and send your ratings here. You can click the Complaint button if you have Complaint to the selected PUV plate.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 9:
                image_tutorial.setImageResource(R.drawable.screenshot_10);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                ind4.setImageResource(R.drawable.unselected);
                ind5.setImageResource(R.drawable.unselected);
                ind6.setImageResource(R.drawable.unselected);
                ind7.setImageResource(R.drawable.unselected);
                ind8.setImageResource(R.drawable.unselected);
                ind9.setImageResource(R.drawable.unselected);
                ind10.setImageResource(R.drawable.selected);

                title.setText("Send Complaint");
                description.setText("Fill the form and send your complaint to the selected plate number. Providing image is important for the credibility of your complaint.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
