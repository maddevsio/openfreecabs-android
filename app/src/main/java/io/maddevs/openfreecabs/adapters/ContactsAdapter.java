package io.maddevs.openfreecabs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.models.ContactModel;
import io.maddevs.openfreecabs.utils.DataStorage;

/**
 * Created by man on 01.10.16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    OnItemClickListener clickListener;

    public ContactsAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContactModel contact = DataStorage.instance.selectedCompanyContacts.get(position);
        holder.contact.setText(contact.contact);

        switch (contact.type) {
            case ContactModel.Sms:
                holder.icon.setImageResource(R.drawable.ic_sms_24dp);
                break;
            case ContactModel.Phone:
                holder.icon.setImageResource(R.drawable.ic_phone_24dp);
                break;
            case ContactModel.Website:
                holder.icon.setImageResource(R.drawable.ic_web_24dp);
                break;
            case ContactModel.Android:
                holder.icon.setImageResource(R.drawable.ic_android_24dp);
                break;
            case ContactModel.Apple:
                holder.icon.setImageResource(R.drawable.ic_apple_24dp);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataStorage.instance.selectedCompanyContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView contact;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            contact = (TextView) itemView.findViewById(R.id.contact);
        }
    }

    public interface OnItemClickListener {
        void onClick(ContactModel item);
    }
}
