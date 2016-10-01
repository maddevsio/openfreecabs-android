package io.maddevs.openfreecabs.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.models.CompanyModel;
import io.maddevs.openfreecabs.models.ContactModel;
import io.maddevs.openfreecabs.utils.DataStorage;

/**
 * Created by rustam on 28.08.16.
 */
public class NearCabsAdapter extends RecyclerView.Adapter<NearCabsAdapter.ViewHolder> {
    OnItemClickListener clickListener;

    public NearCabsAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near_cabs, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CompanyModel company = DataStorage.instance.companies.get(position);
        Picasso.with(holder.itemView.getContext()).load(company.icon).into(holder.icon);
        holder.name.setText(company.name);
        holder.count.setText(String.valueOf(company.drivers.size()));

        holder.count.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(),
                position == 0 ? R.color.green : R.color.textColorSecondary));
        holder.freeCabs.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(),
                position == 0 ? R.color.green : R.color.textColorSecondary));

        String phoneContacts = "";
        String smsContacts = "";
        if (company.contacts != null) {
            for (ContactModel contact : company.contacts) {
                if (contact.type.equals(ContactModel.Phone)) {
                    if (!phoneContacts.isEmpty()) {
                        phoneContacts += ", ";
                    }
                    phoneContacts += contact.contact;
                } else if (contact.type.equals(ContactModel.Sms)) {
                    if (!smsContacts.isEmpty()) {
                        smsContacts += ", ";
                    }
                    smsContacts += contact.contact;
                }
            }
        }

        if (!phoneContacts.isEmpty()) {
            holder.phone.setText(phoneContacts);
        } else {
            holder.phoneContacts.setVisibility(View.GONE);
        }

        if (!smsContacts.isEmpty()) {
            holder.sms.setText(smsContacts);
        } else {
            holder.smsContacts.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(company);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataStorage.instance.companies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name, count, freeCabs, phone, sms;
        View phoneContacts, smsContacts;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.count);
            freeCabs = (TextView) itemView.findViewById(R.id.freeCabs);
            phone = (TextView) itemView.findViewById(R.id.phone);
            sms = (TextView) itemView.findViewById(R.id.sms);
            phoneContacts = itemView.findViewById(R.id.phoneContacts);
            smsContacts = itemView.findViewById(R.id.smsContacts);
        }
    }

    public interface OnItemClickListener {
        void onClick(CompanyModel item);
    }
}
