package ru.korolchuk.catalog.other.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.item.ShoppingItem;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {

    Context context;

    public ShoppingListAdapter(Context context, List<ShoppingItem> items){
        super(context, 0, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        ShoppingItem currentItem = getItem(position);

        ImageView img = (ImageView) listItemView.findViewById(R.id.itemIcon);
        Glide.with(context).load(ShoppingCartAdapter.getLinkImage(currentItem)).into(img);

        TextView name = (TextView) listItemView.findViewById(R.id.itemName);
        name.setText(currentItem.getTitle());

        TextView description = (TextView) listItemView.findViewById(R.id.itemDescription);
        description.setText(currentItem.getDescription());

        TextView cost = (TextView) listItemView.findViewById(R.id.itemPrice);
        cost.setText(currentItem.getPrice());

        return listItemView;
    }
}
//.resizeDimen(R.dimen.forImage, R.dimen.forImage)