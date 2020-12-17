package ru.korolchuk.catalog.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.korolchuk.catalog.other.item.ShoppingItem;
import ru.korolchuk.catalog.presentation.view.IndividualProductView;

@InjectViewState
public class IndividualProductPresenter extends MvpPresenter<IndividualProductView> {

    private final String TAG = IndividualProductPresenter.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private DataSnapshot dataSnapshot;

    private ArrayList<ShoppingItem> cartItems;
    private Boolean isCartEmpty = true;
    private  Boolean isItemAlreadyInCart = false;
    int indexOfAlreadyPresentItem = -1;
    ShoppingItem item;

    private int quantity=1;

//    @Override
//    protected void onFirstViewAttach() {
//        super.onFirstViewAttach();
//
//    }

    public IndividualProductPresenter(ShoppingItem shoppingItem) {

        item = shoppingItem;

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    myRef = database.getReference("users/"+user.getUid());

                    // adding value event listener for myRef
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            IndividualProductPresenter.this.dataSnapshot = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

        getViewState().setName(item.getTitle());
        getViewState().setDescription(item.getDescription());
        getViewState().setQuantity(String.valueOf(quantity));
        getViewState().setPrice(item.getPrice());
        getViewState().setImage(item.getProductID());
    }

    public void addToCart() {
        getViewState().setProgressBarVisibility(View.VISIBLE);

        // Probably a redundant if condition below, but I don't trust computers. :P
        if (dataSnapshot.getKey().equals(user.getUid())){
            isCartEmpty = (Boolean) dataSnapshot.child("isCartEmpty").getValue();
            // if is cart empty, create new arraylist when user wants to add to cart
            //      this is to prevent creation of new cart every time user wants to see stuff
            // else create a new cart and update

            if (!isCartEmpty){
                // Get the cart contents and then update as necessary
                cartItems = new ArrayList<>();
                int tempIndex = 0;
                for (DataSnapshot snap : dataSnapshot.child("cartItems").getChildren()){

                    int itemPrice = -1;
                    try{
                        itemPrice = Integer.valueOf(NumberFormat.getCurrencyInstance()
                                .parse(String.valueOf(snap.child("price").getValue()))
                                .toString());
                    } catch (ParseException e){
                        e.printStackTrace();
                    }

                    int productID = Integer.valueOf(snap.child("productID").getValue().toString());

                    if (productID == item.getProductID()){
                        isItemAlreadyInCart = true;
                        indexOfAlreadyPresentItem = tempIndex;
                    }

                    cartItems.add(new ShoppingItem(
                            productID,
                            snap.child("title").getValue().toString(),
                            snap.child("type").getValue().toString(),
                            snap.child("description").getValue().toString(),
                            itemPrice,
                            Integer.valueOf(snap.child("quantity").getValue().toString())
                    ));

                    tempIndex++;
                }
            }
        }

        getViewState().showSnack("Adding to cart " + quantity + " items.");

        // if cart is empty, then create new cart and push when user adds stuff
        if(isCartEmpty){
            cartItems = new ArrayList<>();
            Map<String, Object> cartState = new HashMap<>();
            cartState.put("isCartEmpty", Boolean.FALSE);
            myRef.updateChildren(cartState);
        }

        if (isItemAlreadyInCart){
            cartItems.get(indexOfAlreadyPresentItem)
                    .setQuantity(cartItems.get(indexOfAlreadyPresentItem).getQuantity() + quantity);
        } else {
            item.setQuantity(quantity);
            cartItems.add(item);
        }

        Map<String, Object> cartItemsMap = new HashMap<>();
        cartItemsMap.put("cartItems", cartItems);

        myRef.updateChildren(cartItemsMap);

        getViewState().setProgressBarVisibility(View.GONE);
    }

    public void increment(){
        if (quantity < 5){
            quantity++;
            getViewState().setQuantity(String.valueOf(quantity));
        } else {
            getViewState().showToast("Limit of 5 products only");
        }
    }

    public void decrement(){
        if (quantity > 1){
            quantity--;
            getViewState().setQuantity(String.valueOf(quantity));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
