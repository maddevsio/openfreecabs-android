package io.maddevs.openfreecabs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.utils.DataStorage;

/**
 * Created by rustam on 28.08.16.
 */
public class NearCabsAdapter extends RecyclerView.Adapter<NearCabsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_near_cabs, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(DataStorage.instance.companies.get(position).name);
        holder.count.setText(DataStorage.instance.companies.get(position).drivers.size());
    }

    @Override
    public int getItemCount() {
        return DataStorage.instance.companies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
