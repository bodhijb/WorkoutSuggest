package com.jf2mc1.a015004rexerciseideas.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jf2mc1.a015004rexerciseideas.R;

public class FavWorkoutViewHolder extends RecyclerView.ViewHolder {
    // fav_workout_item.xml
    private TextView txtFavItemType;
    private TextView txtFavItemDesc;
    private ImageButton btnFavItemShare;

    public FavWorkoutViewHolder(@NonNull View itemView) {
        super(itemView);

        txtFavItemDesc = itemView.findViewById(R.id.txt_fav_item_desc);
        btnFavItemShare = itemView.findViewById(R.id.btn_fav_item_share);
        txtFavItemType = itemView.findViewById(R.id.txt_fav_item_type);

    }

    public TextView getTxtFavItemDesc() {
        return txtFavItemDesc;
    }

    public ImageButton getBtnFavItemShare() {
        return btnFavItemShare;
    }

    public TextView getTxtFavItemType() {
        return txtFavItemType;
    }
}
