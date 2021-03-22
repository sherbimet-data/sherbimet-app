package com.project.sherbimet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.sherbimet.Model.Package;
import com.project.sherbimet.R;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    Context context;
    ArrayList<Package> listPackage;

    public PackageAdapter(Context context, ArrayList<Package> listPackage) {
        this.context = context;
        this.listPackage = listPackage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.package_row_item,parent,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Package apackage = listPackage.get(position);
        String name = apackage.getPackage_name();
        String details = apackage.getPackage_details();
        String duration = apackage.getPackage_duration();
        String price = apackage.getPackage_price();

        holder.tvPackagePrice.setText("Price : Rs " + price + "/-");
        holder.tvPackageDuration.setText("Duration : " + duration);
        holder.tvPackageDetails.setText("Details : " + details);
        holder.tvPackageName.setText(name);

    }

    @Override
    public int getItemCount() {
        return listPackage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPackageName,tvPackageDetails,tvPackageDuration,tvPackagePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackageName = itemView.findViewById(R.id.tvPackageName);
            tvPackageDetails = itemView.findViewById(R.id.tvPackageDetails);
            tvPackageDuration = itemView.findViewById(R.id.tvPackageDuration);
            tvPackagePrice = itemView.findViewById(R.id.tvPackagePrice);
        }
    }

}
