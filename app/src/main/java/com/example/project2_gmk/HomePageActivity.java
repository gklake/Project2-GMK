package com.example.project2_gmk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2_gmk.adminActivities.AdminActivity;
import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    Button buttonBrowse;
    Button buttonSearch;
    Button buttonViewCart;
    Button buttonSignOut;
    Button buttonDeleteAccount;
    Button buttonAdmin;
    TextView welcomeUser;
    SharedPreferences sharedPreferences;

    UserDAO userDAO;
    User user;

    ItemDAO itemDAO;
    List<Item> items;


    private static final String SHARED_PREF_NAME = "pref";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.hide();

        startAnimatedBackground();

        implementUserDAO();

        implementItemDAO();

        wireUpDisplay();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME, null);
        String password = sharedPreferences.getString(KEY_PASSWORD, null);
        user = new User(name, password, isAdmin(name, password));
        if(name != null){
            welcomeUser.setText("Welcome " + name + " :)");
        }


        if(name.equals("admin2") && password.equals("admin2")){
            //Admin button will only be visible if the admin is logged in
            buttonAdmin.setVisibility(View.VISIBLE);
        }
        buttonAdmin.setOnClickListener(v -> startActivity(new Intent(HomePageActivity.this, AdminActivity.class)));

        buttonSearch.setOnClickListener(v -> startActivity(new Intent(HomePageActivity.this, SearchItemActivity.class)));

        buttonDeleteAccount.setOnClickListener(v -> deleteAccountAlertDialog());

        buttonSignOut.setOnClickListener(v -> signOutAlertDialog());

        buttonBrowse.setOnClickListener(v -> startActivity(new Intent(HomePageActivity.this, BrowseItemsActivity.class)));

        buttonViewCart.setOnClickListener(v -> startActivity(new Intent(HomePageActivity.this, ViewCartActivity.class)));

    }

    private void signOutAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Confirming Account Sign Out")
                    .setMessage("Are you sure you would like to sign out of your account?");
        alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            StyleableToast.makeText(HomePageActivity.this, "Logged Out Successfully", R.style.specialToast).show();
            //Going to MainActivity.java(Log In Screen)
            startActivity(new Intent(HomePageActivity.this, MainActivity.class));
            finish();
        });
        alertBuilder.setNegativeButton("Cancel", (dialog, which) ->
            //Dismissing dialog
            StyleableToast.makeText(HomePageActivity.this, "You will not be signed out. :)", R.style.specialToast).show());
        alertBuilder.setCancelable(true);
        alertBuilder.create().show();
    }

    private void startAnimatedBackground() {
        ConstraintLayout constraintLayout = findViewById(R.id.gradientLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
        items = itemDAO.getItems();

        if(items.size() <= 0){
            //Creating all items if this is the first time the app is being run
            //Pre-built Keyboards
            Item blackKeyboard = new Item("Black Keyboard", 75.0, "All black keyboard with Gateron Black switches, black OEM profile shine through PBT keycaps in a matte black case", 50);
            items.add(blackKeyboard);
            Item whiteKeyboard = new Item("White Keyboard", 75.0, "All white keyboard with Gateron Black switches, white OEM profile shine through PBT keycaps in a matte white case", 50);
            items.add(whiteKeyboard);
            Item redKeyboard = new Item("Red Keyboard", 75.0, "All red keyboard with Gateron Black switches, red OEM profile shine through PBT keycaps in a matte red case", 50);
            items.add(redKeyboard);
            Item purpleKeyboard = new Item("Purple Keyboard", 75.0, "All purple keyboard with Gateron Black switches, purple OEM profile shine through PBT keycaps in a matte purple case", 50);
            items.add(purpleKeyboard);
            Item royalBlueKeyboard = new Item("Royal Blue Keyboard", 75.0, "All royal blue keyboard with Gateron Black switches, royal blue OEM profile shine through PBT keycaps in a matte royal blue case", 50);
            items.add(royalBlueKeyboard);
            Item pinkKeyboard = new Item("Pink Keyboard", 75.0, "All pink keyboard with Gateron Black switches, pink OEM profile shine through PBT keycaps in a matte pink case", 50);
            items.add(pinkKeyboard);
            Item greenKeyboard = new Item("Green Keyboard", 75.0, "All green keyboard with Gateron Black switches, green OEM profile shine through PBT keycaps in a matte green case", 50);
            items.add(greenKeyboard);
            Item babyBlueKeyboard = new Item("Baby Blue Keyboard", 75.0, "All baby blue keyboard with Gateron Black switches, baby blue OEM profile shine through PBT keycaps in a matte baby blue case", 50);
            items.add(babyBlueKeyboard);

            //Keycaps
            Item blackKeycaps = new Item("Black Keycaps", 50.0, "OEM profile black shine through PBT keycaps", 50);
            items.add(blackKeycaps);
            Item whiteKeycaps = new Item("White Keycaps", 50.0, "OEM profile white shine through PBT keycaps", 50);
            items.add(whiteKeycaps);
            Item grayKeycaps = new Item("Gray Keycaps", 50.0, "OEM profile gray shine through PBT keycaps", 50);
            items.add(grayKeycaps);
            Item redKeycaps = new Item("Red Keycaps", 50.0, "OEM profile red shine through PBT keycaps", 50);
            items.add(redKeycaps);
            Item purpleKeycaps = new Item("Purple Keycaps", 50.0, "OEM profile purple shine through PBT keycaps", 50);
            items.add(purpleKeycaps);
            Item royalBlueKeycaps = new Item("Royal Blue Keycaps", 50.0, "OEM profile royal blue shine through PBT keycaps", 50);
            items.add(royalBlueKeycaps);
            Item pinkKeycaps = new Item("Pink Keycaps", 50.0, "OEM profile pink shine through PBT keycaps", 50);
            items.add(pinkKeycaps);
            Item greenKeycaps = new Item("Green Keycaps", 50.0, "OEM profile green shine through PBT keycaps", 50);
            items.add(greenKeycaps);
            Item babyBlueKeycaps = new Item("Baby Blue Keycaps", 50.0, "OEM profile baby blue shine through PBT keycaps", 50);
            items.add(babyBlueKeycaps);

            //Switches
            Item nKCreams = new Item("NovelKeys Cream Switches", 7.0, "NovelKeys Cream linear switches, comes in sets of 10", 50);
            items.add(nKCreams);
            Item gateronReds = new Item("Gateron Red Switches", 4.0, "Gateron Red linear switches, comes in sets of 10", 50);
            items.add(gateronReds);
            Item gateronBlues = new Item("Gateron Blue Switches", 4.0, "Gateron Blue clicky switches, comes in sets of 10", 50);
            items.add(gateronBlues);
            Item gateronBrowns = new Item("Gateron Brown Switches", 4.0, "Gateron Brown tactile switches, comes in sets of 10", 50);
            items.add(gateronBrowns);
            Item gateronYellows = new Item("Gateron Yellow Switches", 4.0, "Gateron Yellow linear switches, comes in sets of 10", 50);
            items.add(gateronYellows);
            Item gateronGreens = new Item("Gateron Green Switches", 4.0, "Gateron Green clicky switches, comes in sets of 10", 50);
            items.add(gateronGreens);
            Item gateronBlacks = new Item("Gateron Black Switches", 4.0, "Gateron Black linear switches, comes in sets of 10", 50);
            items.add(gateronBlacks);
            Item gateronWhites = new Item("Gateron White Switches", 4.0, "Gateron White linear switches, comes in sets of 10", 50);
            items.add(gateronWhites);
            Item cherryMXReds = new Item("CherryMX Red Switches", 4.0, "CherryMX Red linear switches, comes in sets of 10", 50);
            items.add(cherryMXReds);
            Item cherryMXBlacks = new Item("CherryMX Black Switches", 4.0, "CherryMX Black linear switches, comes in sets of 10", 50);
            items.add(cherryMXBlacks);
            Item cherryMXBrowns = new Item("CherryMX Brown Switches", 4.0, "CherryMX Brown tactile switches, comes in sets of 10", 50);
            items.add(cherryMXBrowns);
            Item cherryMXClears = new Item("CherryMX Clear Switches", 4.0, "CherryMX Clear tactile switches, comes in sets of 10", 50);
            items.add(cherryMXClears);
            Item cherryMXBlues = new Item("CherryMX Blue Switches", 4.0, "CherryMX Blue clicky switches, comes in sets of 10", 50);
            items.add(cherryMXBlues);
            Item cherryMXWhites = new Item("CherryMX White Switches", 4.0, "CherryMX White clicky switches, comes in sets of 10", 50);
            items.add(cherryMXWhites);
            Item cherryMXGreens = new Item("CherryMX Green Switches", 4.0, "CherryMX Green clicky switches, comes in sets of 10", 50);
            items.add(cherryMXGreens);


            //Cases
            Item frostedAcrylicCase = new Item("Frosted Acrylic Case", 40.0, "Frosted acrylic case that comes with foam to put in-between case and plate", 50);
            items.add(frostedAcrylicCase);
            Item blackAluminiumCase = new Item("Black Aluminum Case", 75.0, "Black aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(blackAluminiumCase);
            Item blackPlasticCase = new Item("Black Plastic Case", 35.0, "Black plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(blackPlasticCase);
            Item silverAluminiumCase = new Item("Silver Aluminum Case", 75.0, "Silver aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(silverAluminiumCase);
            Item grayPlasticCase = new Item("Gray Plastic Case", 35.0, "Gray plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(grayPlasticCase);
            Item whiteAluminiumCase = new Item("White Aluminum Case", 75.0, "White aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(whiteAluminiumCase);
            Item whitePlasticCase = new Item("White Plastic Case", 35.0, "White plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(whitePlasticCase);
            Item blueAluminiumCase = new Item("Blue Aluminum Case", 75.0, "Blue aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(blueAluminiumCase);
            Item bluePlasticCase = new Item("Blue Plastic Case", 35.0, "Blue plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(bluePlasticCase);
            Item redAluminiumCase = new Item("Red Aluminum Case", 75.0, "Red aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(redAluminiumCase);
            Item redPlasticCase = new Item("Red Plastic Case", 35.0, "Red plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(redPlasticCase);
            Item pinkAluminiumCase = new Item("Pink Aluminum Case", 75.0, "Pink aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(pinkAluminiumCase);
            Item pinkPlasticCase = new Item("Pink Plastic Case", 35.0, "Pink plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(pinkPlasticCase);
            Item purpleAluminiumCase = new Item("Purple Aluminum Case", 75.0, "Purple aluminium keyboard case that comes with foam to put in-between case and plate",50);
            items.add(purpleAluminiumCase);
            Item purplePlasticCase = new Item("Purple Plastic Case", 35.0, "Purple plastic keyboard case that comes with foam to put in-between case and plate",50);
            items.add(purplePlasticCase);

            //Plates
            Item brassPlate = new Item("Brass Plate", 40.0, "Brass plate that provides more weight and stability for your keyboard", 50);
            items.add(brassPlate);
            Item polycarbonatePlate = new Item("Polycarbonate Plate", 20.0, "Polycarbonate plate that provides stability for your keyboard", 50);
            items.add(polycarbonatePlate);
            Item aluminiumPlate = new Item("Aluminium Plate", 20.0, "Aluminium plate that provides stability for your keyboard", 50);
            items.add(aluminiumPlate);

            //Hot-Swappable PCB
            Item blackPCB = new Item("Black PCB", 55.0, "Hot-Swappable black PCB with south facing RGB lights and USB Type-C port", 50);
            items.add(blackPCB);
            Item whitePCB = new Item("White PCB", 55.0, "Hot-Swappable white PCB with south facing RGB lights and USB Type-C port", 50);
            items.add(whitePCB);

            //Tool Kit
            Item blackToolKit = new Item("Black Tool Kit", 35.0, "Includes: Lube station with 2 components, 1 switch stem holder, 2 lube brushes, a switch opener, and 1 tweezer", 50);
            items.add(blackToolKit);
            Item whiteToolKit = new Item("White Tool Kit", 35.0, "Includes: Lube station with 2 components, 1 switch stem holder, 2 lube brushes, a switch opener, and 1 tweezer", 50);
            items.add(whiteToolKit);
            Item grayToolKit = new Item("Gray Tool Kit", 35.0, "Includes: Lube station with 2 components, 1 switch stem holder, 2 lube brushes, a switch opener, and 1 tweezer", 50);
            items.add(grayToolKit);

            //Coiled Keyboard Cables
            Item blackCoiledCable = new Item("Black Coiled Cable", 25.0, "5ft long(not including coil length) USB Type-C keyboard cable", 50);
            items.add(blackCoiledCable);
            Item whiteCoiledCable = new Item("White Coiled Cable", 25.0, "5ft long(not including coil length) USB Type-C keyboard cable", 50);
            items.add(whiteCoiledCable);
            Item grayCoiledCable = new Item("Gray Coiled Cable", 25.0, "5ft long(not including coil length) USB Type-C keyboard cable", 50);
            items.add(grayCoiledCable);
            Item babyBlueCoiledCable = new Item("Baby Blue Coiled Cable", 25.0, "5ft long(not including coil length) USB Type-C keyboard cable", 50);
            items.add(babyBlueCoiledCable);
            Item pinkCoiledCable = new Item("Pink Coiled Cable", 25.0, "5ft long(not including coil length) USB Type-C keyboard cable", 50);
            items.add(pinkCoiledCable);

            //Special Escape Keys
            Item timeStoneEsc = new Item("Time Stone Esc Keycap", 300.0, "Green escape keycap with the Time Stone on the top", 1);
            items.add(timeStoneEsc);
            Item powerStoneEsc = new Item("Power Stone Esc Keycap", 300.0, "Purple escape keycap with the Power Stone on the top", 1);
            items.add(powerStoneEsc);
            Item mindStoneEsc = new Item("Mind Stone Esc Keycap", 300.0, "Yellow escape keycap with the Mind Stone on the top", 1);
            items.add(mindStoneEsc);
            Item realityStoneEsc = new Item("Reality Stone Esc Keycap", 300.0, "Red escape keycap with the Reality Stone on the top", 1);
            items.add(realityStoneEsc);
            Item soulStoneEsc = new Item("Soul Stone Esc Keycap", 300.0, "Orange escape keycap with the Soul Stone on the top(sacrifice has been previously settled)", 1);
            items.add(soulStoneEsc);
            Item gonBadgeEsc = new Item("Gon's Badge Esc Keycap", 15.0, "Black escape keycap with Gon's badge(405) printed on the top", 50);
            items.add(gonBadgeEsc);
            Item killuaBadgeEsc = new Item("Killua's Badge Esc Keycap", 15.0, "Black escape keycap with Killua's badge(99) printed on the top", 50);
            items.add(killuaBadgeEsc);
            Item kurapikaBadgeEsc = new Item("Kurapika's Badge Esc Keycap", 15.0, "Black escape keycap with Kurapika's badge(404) printed on the top", 50);
            items.add(kurapikaBadgeEsc);
            Item leorioBadgeEsc = new Item("Leorio's Badge Esc Keycap", 15.0, "Black escape keycap with Leorio's badge(403) printed on the top", 50);
            items.add(leorioBadgeEsc);
            Item hisokaBadgeEsc = new Item("Hisoka's Badge Esc Keycap", 15.0, "Black escape keycap with Hisoka's badge(44) printed on the top", 50);
            items.add(hisokaBadgeEsc);
            Item hunterAssociationEsc = new Item("Hunter Association Esc Keycap", 15.0, "White escape keycap with the Hunter Association logo printed on the top", 50);
            items.add(hunterAssociationEsc);
            Item surveyCorpsEsc = new Item("Survey Corps Esc Keycap", 15.0, "Military green escape keycap with the Survey Corps logo printed on the top", 50);
            items.add(surveyCorpsEsc);
            Item koroSenseiEsc = new Item("Koro Sensei Esc Keycap", 15.0, "Yellow escape keycap with Koro Sensei's face printed on the top", 50);
            items.add(koroSenseiEsc);
            Item hiddenLeafEsc = new Item("Hidden Leaf Village Esc Keycap", 15.0, "Orange escape keycap with the Hidden Leaf Village symbol printed on the top", 50);
            items.add(hiddenLeafEsc);
            Item kanekiKenMaskEsc = new Item("Kaneki Ken's Mask Esc Keycap", 15.0, "White escape keycap with Kaneki Ken's mask printed on the top", 50);
            items.add(kanekiKenMaskEsc);

            //Insert each item to the ItemDAO
            for(Item item : items){
                itemDAO.insert(item);
            }
        }
    }

    private void wireUpDisplay() {
        buttonBrowse = findViewById(R.id.buttonBrowse);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonViewCart = findViewById(R.id.buttonViewCart);
        buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonAdmin = findViewById(R.id.buttonAdmin);
        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);
        welcomeUser = findViewById(R.id.welcomeUser);
    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void deleteAccountAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Confirming Account Deletion")
                    .setMessage("Are you sure you would like to delete your account?");
        alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
            //Deleting user from the database
            userDAO.delete(user);
            StyleableToast.makeText(HomePageActivity.this, "Account has been deleted.", R.style.specialToast).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //Clearing sharedPreferences and returning to login page
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomePageActivity.this, MainActivity.class));
            finish();
        });
        alertBuilder.setNegativeButton("Cancel", (dialog, which) ->
            //Dismissing alert dialog
            StyleableToast.makeText(HomePageActivity.this, "Account will not be deleted :)", R.style.specialToast).show());
        alertBuilder.setCancelable(true);
        alertBuilder.create().show();
    }

    private boolean isAdmin(String username, String password){
        return username.equals("admin2") && password.equals("admin2");
    }

}