package ru.korolchuk.catalog.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.korolchuk.catalog.other.item.ShoppingItem;
import ru.korolchuk.catalog.presentation.view.MainAppView;

@InjectViewState
public class MainAppPresenter extends MvpPresenter<MainAppView> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("items");
    private Boolean exit = false;
    private ArrayList<ShoppingItem> shoppingItems;

    private final String TAG = MainAppPresenter.class.getSimpleName();
    public MainAppPresenter() {
        myRef.addValueEventListener(new ValueEventListener() {
            // This listener is only for database with reference of key "items"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Now the Shopping List gets updated whenever the data changes in the server
                shoppingItems = getAllItems(dataSnapshot);
                getViewState().setShoppingItems(shoppingItems);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static ArrayList<ShoppingItem> getAllItems(DataSnapshot dataSnapshot){

        ArrayList<ShoppingItem> items  = new ArrayList<ShoppingItem>();

        for (DataSnapshot item : dataSnapshot.getChildren()) {

            items.add(new ShoppingItem(
                    Integer.valueOf(item.child("productID").getValue().toString()),
                    item.child("name").getValue().toString(),
                    item.child("type").getValue().toString(),
                    item.child("description").getValue().toString(),
                    Integer.valueOf(item.child("price").getValue().toString()),
                    Integer.valueOf(item.child("quantity").getValue().toString())
            ));
        }
        return items;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public ShoppingItem getShopingItem(int i){
        return shoppingItems.get(i);
    }
}
