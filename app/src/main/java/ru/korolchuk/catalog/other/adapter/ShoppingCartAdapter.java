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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.item.ShoppingItem;

public class ShoppingCartAdapter extends ArrayAdapter {
    Context context;
    ArrayList<ShoppingItem> items;

    public ShoppingCartAdapter(Context context, List<ShoppingItem> items){
        super(context, 0, items);
        this.context = context;
        this.items = (ArrayList<ShoppingItem>) items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_item, parent, false
            );
        }

        ShoppingItem currentItem = (ShoppingItem) getItem(position);
        ImageView img = (ImageView) listItemView.findViewById(R.id.cartItemIcon);
        Glide.with(context).load(getLinkImage(currentItem)).into(img);

        ((TextView) listItemView.findViewById(R.id.cartItemName))
                .setText(currentItem.getTitle());

        String x = "x " + String.valueOf(currentItem.getQuantity());
        ((TextView) listItemView.findViewById(R.id.cartItemQuantity))
                .setText(x);

        int itemPrice=0;
        try{
            itemPrice = Integer.valueOf(NumberFormat.getCurrencyInstance()
                    .parse(String.valueOf(currentItem.getPrice()))
                    .toString());
        } catch (ParseException e){
            e.printStackTrace();
        }
        ((TextView) listItemView.findViewById(R.id.cartItemPrice))
                .setText(NumberFormat.getCurrencyInstance().format(itemPrice));

        ((TextView) listItemView.findViewById(R.id.cartItemTotal))
                .setText(NumberFormat.getCurrencyInstance().format(itemPrice * currentItem.getQuantity()));

        // No idea how to implement remove individual item from cart
        // Appreciated if anyone can fix it.
//        ImageView removeFromCart = (ImageView) listItemView.findViewById(R.id.removeFromCart);
//        removeFromCart.setImageResource(R.drawable.ic_clear_black_24dp);
//        removeFromCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                items.remove()
//            }
//        });

        return listItemView;
    }
    public static String getLinkImage(ShoppingItem item)
    {
        String[] urls = new String[] {
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/0.jpg?alt=media&token=698cb551-6863-4964-982a-5490be43bc1d",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/1.jpg?alt=media&token=c5e4669e-05c1-43b7-8763-ad07e51ee7b4",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/2.jpg?alt=media&token=4313c61f-5b88-4729-af5c-c73bded00950",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/3.jpg?alt=media&token=4bbbb0f9-ea55-40e8-b5e6-91ac186589c8",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/4.jpg?alt=media&token=bc7e2f48-c9ae-4f90-871c-5f2313708591",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/5.jpg?alt=media&token=9a6950ab-d8a7-4b1b-9c5e-a4e5acfa5d7d",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/6.jpg?alt=media&token=57b76824-b3c5-47da-8ef0-9f1108af8783",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/7.jpg?alt=media&token=751384be-2565-4ff8-bb99-933d59b58c37",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/8.jpg?alt=media&token=49456e63-d354-4435-985a-fb43bf47dc52",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/9.jpg?alt=media&token=2e59a06c-d47a-43dd-8d6b-9d0955c641ff",
        };
        return urls[item.getProductID()];
    }
    public static String getLinkImage(int item)
    {
        String[] urls = new String[] {
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/0.jpg?alt=media&token=698cb551-6863-4964-982a-5490be43bc1d",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/1.jpg?alt=media&token=c5e4669e-05c1-43b7-8763-ad07e51ee7b4",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/2.jpg?alt=media&token=4313c61f-5b88-4729-af5c-c73bded00950",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/3.jpg?alt=media&token=4bbbb0f9-ea55-40e8-b5e6-91ac186589c8",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/4.jpg?alt=media&token=bc7e2f48-c9ae-4f90-871c-5f2313708591",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/5.jpg?alt=media&token=9a6950ab-d8a7-4b1b-9c5e-a4e5acfa5d7d",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/6.jpg?alt=media&token=57b76824-b3c5-47da-8ef0-9f1108af8783",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/7.jpg?alt=media&token=751384be-2565-4ff8-bb99-933d59b58c37",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/8.jpg?alt=media&token=49456e63-d354-4435-985a-fb43bf47dc52",
                "https://firebasestorage.googleapis.com/v0/b/test-b0bb9.appspot.com/o/9.jpg?alt=media&token=2e59a06c-d47a-43dd-8d6b-9d0955c641ff",
        };
        return urls[item];
    }
}
