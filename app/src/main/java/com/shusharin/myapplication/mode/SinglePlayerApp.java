package com.shusharin.myapplication.mode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.R;
import com.shusharin.myapplication.card.Card;
import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.card.CardWithColor;
import com.shusharin.myapplication.card.Color;
import com.shusharin.myapplication.card.SpecialCardWithBlack;
import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.user.Bot;
import com.shusharin.myapplication.user.User;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerApp extends AppCompatActivity implements View.OnClickListener {
    public static final ArrayList<CardViewer> deck = new ArrayList<>();
    protected static final int quantityStartCard = 7;
    private static final Bot bot = new Bot();
    private static final int durationAnimation = 500;
    public static Conservation conservation;
    protected static boolean isPressed = false;
    protected static boolean isTakeCard = false;
    private static Animation translateBottomToCenter;
    private static Animation translateTopToCenter;
    private static Animation growFromMiddleXAxis;
    private static Animation shrinkToMiddleXAxis;
    private final Human player = new Human();
    public View lastCardOnTheTable;
    public View cardOnTheTable;
    public View cardOnTheTableBack;
    public View cardsInHand;
    public TextView quantityCardsInHand;
    public TextView playerCurrent;
    public TextView currentPlayerStart;
    public TextView quantityCardsInHandTop;
    public TextView playerTop;
    public ArrayList<CardViewer> table = new ArrayList<>();
    public AlertDialog dialogConfirmationCardSelection;
    protected View blackView;
    protected View blackCardsInHand;
    protected Button startTurn;
    protected SharedPreferences preferences;
    private Animation scaleDown;
    private Animation scaleUp;
    private boolean isFirstMove = true;
    private int numberSelectionCard;
    private CardAdapter cardAdapter;

    public static void createDeck() {
        deck.clear();
        for (int j = 0; j < Color.values().length - 1; j++) {
            for (int i = 0; i < CardWithColor.values().length; i++) {
                for (int k = 0; k < 2; k++) {
                    deck.add(new CardViewer(new Card(CardWithColor.values()[i], Color.values()[j])));
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < SpecialCardWithBlack.values().length; j++) {
                deck.add(new CardViewer(new Card(SpecialCardWithBlack.values()[j])));
            }
        }
    }

    private void turnBot() {
        isPressed = true;
        if (bot.getCardsInHand().size() == 0) {
            blackView.setVisibility(View.VISIBLE);
            startTurn.setActivated(false);
            playerTop.setText(R.string.winner);
            String winner = playerTop.getText().toString();
            playerTop.setText(R.string.bot);
            String player = playerTop.getText().toString();
            currentPlayerStart.setText(winner + player);
            currentPlayerStart.setVisibility(View.VISIBLE);
            currentPlayerStart.setTextSize(20);
            conservation.setFinished(true);
            return;
        }

        setAvailableCardsBot(bot.getCardsInHand(), getCardOnTheTable());
        if (takeCardIfNeedBot(bot.getCardsInHand())) {
            cardOnTheTable.setVisibility(View.INVISIBLE);
            cardOnTheTableBack.startAnimation(translateTopToCenter);

            CardViewer cardViewer = bot.turn();

            table.add(cardViewer);
            bot.getCardsInHand().remove(cardViewer);

            cardOnTheTable.setBackground(cardViewer.getDrawable(this));
            new Handler().postDelayed(() -> {
                cardOnTheTableBack.setVisibility(View.VISIBLE);
                cardOnTheTableBack.startAnimation(shrinkToMiddleXAxis);
                new Handler().postDelayed(() -> {
                    cardOnTheTableBack.setVisibility(View.INVISIBLE);
                    cardOnTheTable.setVisibility(View.VISIBLE);
                    cardOnTheTable.startAnimation(growFromMiddleXAxis);
                    new Handler().postDelayed(() -> {
                        lastCardOnTheTable.setBackground(cardOnTheTable.getBackground());
                        switch (getCardOnTheTable().getCard().getId()) {
                            case 12:
                                for (int i = 0; i < 2; i++) {
                                    player.getCardsInHand().add(peekCard());
                                }
                            case 11:
                            case 10:
                                turnBot();
                                break;
                            case 14:
                                getCardOnTheTable().getCard().setColor(bot.getPreferredColor());
                                cardOnTheTable.setBackground(cardViewer.getDrawable(this));
                                lastCardOnTheTable.setBackground(cardViewer.getDrawable(this));
                                break;
                            case 13:
                                getCardOnTheTable().getCard().setColor(bot.getPreferredColor());
                                cardOnTheTable.setBackground(cardViewer.getDrawable(this));
                                lastCardOnTheTable.setBackground(cardViewer.getDrawable(this));
                                for (int i = 0; i < 4; i++) {
                                    player.getCardsInHand().add(peekCard());
                                }
                                turnBot();
                                break;
                        }
                        isPressed = false;
                        updatePlayerCard();
                    }, durationAnimation);
                }, durationAnimation);
            }, 2500);
        }
        quantityCardsInHandTop.setText(String.valueOf(bot.getCardsInHand().size()));
        quantityCardsInHand.setText(String.valueOf(player.getCardsInHand().size()));
        updatePlayerCard();

    }

    private void updatePlayerCard() {
        setAvailableCards(getCardsInHandCurrentPlayer(), getCardOnTheTable());
        takeCardIfNeed(getCardsInHandCurrentPlayer());
    }


    protected CardViewer peekCard() {
        return peekCard(0);
    }

    protected CardViewer peekCard(int index) {
        CardViewer cardView = deck.get(index);
        deck.remove(index);
        return cardView;
    }

    protected CardViewer getCardOnTheTable() {
        return table.get(table.size() - 1);
    }

    protected boolean takeCardIfNeedBot(@NonNull ArrayList<CardViewer> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isAvailable()) {
                return true;
            }
        }
        CardViewer newCard = peekCard();
        cards.add(newCard);
        CardViewer cardOnTheTable = getCardOnTheTable();
        if (!(isAvailable(cardOnTheTable, newCard))) {
            new Handler().postDelayed(() -> isPressed = false, 1000);
            cards.get(cards.size() - 1).setAvailable(false);
            return false;
        }

        return true;
    }

    public void mixDeck() {
        Random random = new Random();
        CardViewer card;
        for (int i = 0; i < deck.size() - 1; i++) {
            int index = random.nextInt(i + 1);
            card = peekCard(index);
            deck.add(index, peekCard(i));
            deck.add(i, card);
        }
    }

    public void afterSelectingCard(boolean isOnlyBot) {
        isPressed = true;
        if (!isOnlyBot) {
            cardOnTheTable.startAnimation(translateBottomToCenter);
            isTakeCard = false;


            new Handler().postDelayed(() -> {
                lastCardOnTheTable.setBackground(cardOnTheTable.getBackground());
                if (player.getCardsInHand().size() == 0) {
                    blackView.setVisibility(View.VISIBLE);
                    startTurn.setActivated(false);
                    playerTop.setText(R.string.winner);
                    String winner = playerTop.getText().toString();
                    playerTop.setText(R.string.you);
                    String player = playerTop.getText().toString();
                    currentPlayerStart.setText(winner + player);
                    currentPlayerStart.setVisibility(View.VISIBLE);
                    currentPlayerStart.setTextSize(20);
                    conservation.setFinished(true);
                    return;
                }

                switch (getCardOnTheTable().getCard().getId()) {
                    case 12:
                        for (int i = 0; i < 2; i++) {
                            bot.getCardsInHand().add(peekCard());
                        }
                    case 11:
                    case 10:
                        isPressed = false;
                        break;
                    case 13:
                        for (int i = 0; i < 4; i++) {
                            bot.getCardsInHand().add(peekCard());
                        }
                        isPressed = false;
                        break;
                    case 14:
                    default:
                        turnBot();
                }
                quantityCardsInHandTop.setText(String.valueOf(bot.getCardsInHand().size()));
                quantityCardsInHand.setText(String.valueOf(player.getCardsInHand().size()));
                updatePlayerCard();
            }, 2500);
        } else {
            turnBot();
            updatePlayerCard();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        player.setCardsInHand(new Hand());
        isPressed = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        growFromMiddleXAxis = AnimationUtils.loadAnimation(this, R.anim.grow_from_middle_x_axis);
        shrinkToMiddleXAxis = AnimationUtils.loadAnimation(this, R.anim.shrink_to_middle_x_axis);
        translateBottomToCenter = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_to_center);
        translateTopToCenter = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);

        growFromMiddleXAxis.setDuration(durationAnimation);
        shrinkToMiddleXAxis.setDuration(durationAnimation);


        lastCardOnTheTable = findViewById(R.id.lastCardOnTheTable);
        cardOnTheTable = findViewById(R.id.cardOnTheTable);
        cardOnTheTableBack = findViewById(R.id.cardOnTheTableCover);
        cardsInHand = findViewById(R.id.cardsInHand);
        quantityCardsInHand = findViewById(R.id.quantityCardsInHand);
        blackView = findViewById(R.id.blackView);
        blackCardsInHand = findViewById(R.id.blackCardsInHand);
        startTurn = findViewById(R.id.startTurn);

        playerTop = findViewById(R.id.playerTop);
        currentPlayerStart = findViewById(R.id.currentPlayerStart);
        quantityCardsInHandTop = findViewById(R.id.quantityCardsInHandTop);

        playerCurrent = findViewById(R.id.currentPlayer);

        playerCurrent.setText(R.string.you);

        playerTop.setText(R.string.bot);

        currentPlayerStart.setText(R.string.player);

        conservation = ContinueApp.conservations.get(getIntent().getIntExtra("NUMBER_CONSERVATION", 0));
        preferences = getSharedPreferences(getIntent().getStringExtra(conservation.getName()), Context.MODE_PRIVATE);

        table.clear();
        bot.getCardsInHand().clear();
        player.getCardsInHand().clear();

        RecyclerView listConservation = findViewById(R.id.list);
        cardAdapter = new CardAdapter(this);
        listConservation.setAdapter(cardAdapter);

        if (conservation.isContinue()) {
            loadData();
        } else {
            createDeck();
            mixDeck();

            handOutCard();

            table.add(peekCard());
        }

        quantityCardsInHandTop.setText(String.valueOf(bot.getCardsInHand().size()));

        if (conservation.isFinished()) {
            cardsInHand.setActivated(false);
        }

        cardOnTheTable.setBackground(getCardOnTheTable().getDrawable(this));
        cardOnTheTable.setVisibility(View.INVISIBLE);
        lastCardOnTheTable.setBackground(cardOnTheTable.getBackground());
        lastCardOnTheTable.setVisibility(View.INVISIBLE);
        onCreateDialog();
        new Handler().postDelayed(() -> isPressed = false, 250);
    }

    protected void loadData() {
        if (preferences.contains(conservation.getName())) {
            loadCardArray("DECK_SIZE", deck, "DECK_CARD_ID", "DECK_CARD_COLOR");
            loadCardArray("TABLE_SIZE", table, "TABLE_CARD_ID", "TABLE_CARD_COLOR");
            loadCardArray("PLAYER_SIZE", player.getCardsInHand(), "PLAYER_CARD_ID", "PLAYER_CARD_COLOR");
            loadCardArray("BOT_SIZE", bot.getCardsInHand(), "BOT_CARD_ID", "BOT_CARD_COLOR");
        }
    }

    protected void loadCardArray(String deck_size, ArrayList<CardViewer> deck, String deck_card_id, String deck_card_color) {
        int size = 0;
        if (preferences.contains(deck_size)) {
            size = preferences.getInt(deck_size, 0);
        }
        for (int i = 0; i < size; i++) {
            deck.add(new CardViewer(new Card(
                    preferences.getInt(deck_card_id + i, 0),
                    Color.values()[preferences.getInt(deck_card_color + i, 0)]
            )));
        }
    }

    protected void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(conservation.getName(), conservation.getName());

        saveCardArray(editor, "DECK_SIZE", deck, "DECK_CARD_COLOR", "DECK_CARD_ID");
        saveCardArray(editor, "TABLE_SIZE", table, "TABLE_CARD_COLOR", "TABLE_CARD_ID");
        saveCardArray(editor, "PLAYER_SIZE", player.getCardsInHand(), "PLAYER_CARD_COLOR", "PLAYER_CARD_ID");
        saveCardArray(editor, "BOT_SIZE", bot.getCardsInHand(), "BOT_CARD_COLOR", "BOT_CARD_ID");
        editor.apply();
    }

    protected void saveCardArray(SharedPreferences.Editor editor, String deck_size, ArrayList<CardViewer> deck, String deck_card_color, String deck_card_id) {
        editor.putInt(deck_size, deck.size());
        for (int i = 0; i < deck.size(); i++) {
            editor.putInt(deck_card_color + i, deck.get(i).getCard().getColor().getNumber());
            editor.putInt(deck_card_id + i, deck.get(i).getCard().getId());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void setAvailableCardsBot(ArrayList<CardViewer> cards, CardViewer cardOnTheTable) {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setAvailable(isAvailable(cardOnTheTable, cards.get(i))
            );

        }
    }

    public void setAvailableCards(Hand cards, CardViewer cardOnTheTable) {
        for (int i = 0; i < cards.size(); i++) {
            cards.setAvailable(i, isAvailable(cardOnTheTable, cards.get(i))
            );

        }
    }

    private boolean isAvailable(CardViewer cardOnTheTable, CardViewer cardViewer) {
        return cardViewer.getCard().getColor() == cardOnTheTable.getCard().getColor()
                || cardViewer.getCard().getId() == cardOnTheTable.getCard().getId()
                || cardViewer.getCard().getColor() == Color.BLACK;
    }

    protected Hand getCardsInHandCurrentPlayer() {
        return player.getCardsInHand();
    }

    protected void setBackgroundHand() {
        cardsInHand.setBackground(getCardsInHandCurrentPlayer().get(getCardsInHandCurrentPlayer().size() - 1).getDrawable(this));
        quantityCardsInHand.setText(String.valueOf(getCardsInHandCurrentPlayer().size()));
    }

    protected void handOutCard() {
        for (int i = 0; i < quantityStartCard; i++) {
            player.getCardsInHand().add(peekCard());
            bot.getCardsInHand().add(peekCard());
        }
    }

//    public void onClickHand(View view) {
//        if (!isPressed) {
//            isPressed = true;
//            setAvailableCards(getCardsInHandCurrentPlayer(), getCardOnTheTable());
//            if (takeCardIfNeed(getCardsInHandCurrentPlayer())) {
//                CardsDeckApp.setCards(getCardsInHandCurrentPlayer());
//                Intent intent = new Intent(this, CardsDeckApp.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            new Handler().postDelayed(() -> isPressed = false, 250);
//        }
//    }

    @SuppressLint("NotifyDataSetChanged")
    protected boolean takeCardIfNeed(Hand cards) {
        if (!isTakeCard) {
            isTakeCard = true;
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).isAvailable()) {
                    return true;
                }
            }
            CardViewer newCard = peekCard();
            cards.add(newCard);
            setBackgroundHand();
            CardViewer cardOnTheTable = getCardOnTheTable();
            if (!(isAvailable(cardOnTheTable, newCard))) {
                Toast.makeText(this, "У вас нет подходящих карт для хода, ход передаётся следующему игроку", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(() -> isPressed = false, 1000);
                cards.setAvailable(cards.size() - 1, false);
                afterSelectingCard(true);
                return false;
            }
        }
        return true;
    }

    public void onClickNo(View view) {

    }

    public void onClickStartTurn(View view) {
        if (!isPressed) {
            isPressed = true;

            if (isFirstMove) {
                isFirstMove = false;
                cardOnTheTableBack.startAnimation(shrinkToMiddleXAxis);

                new Handler().postDelayed(() -> {
                    cardOnTheTableBack.setVisibility(View.INVISIBLE);
                    cardOnTheTable.setVisibility(View.VISIBLE);

                    cardOnTheTable.startAnimation(growFromMiddleXAxis);
                    new Handler().postDelayed(() -> {
                        updatePlayerCard();
                        lastCardOnTheTable.setVisibility(View.VISIBLE);
                        isPressed = false;
                    }, durationAnimation);
                }, durationAnimation);
            }


            startTurn.setVisibility(View.GONE);
            blackView.setVisibility(View.GONE);
            blackCardsInHand.setVisibility(View.GONE);
            currentPlayerStart.setVisibility(View.GONE);
            setBackgroundHand();
        }
    }

    private void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        //Создать с кнопками
        @SuppressLint("InflateParams") View viewDialog = inflater.inflate(R.layout.card_dialog, null);
        Button positive = viewDialog.findViewById(R.id.positive);

        Button positiveRed = viewDialog.findViewById(R.id.positiveRed);
        Button positiveGreen = viewDialog.findViewById(R.id.positiveGreen);
        Button positiveYellow = viewDialog.findViewById(R.id.positiveYellow);
        Button positiveBlue = viewDialog.findViewById(R.id.positiveBlue);
        Button negative = viewDialog.findViewById(R.id.negative);

        positive.setOnClickListener(this);
        positiveGreen.setOnClickListener(this);
        positiveYellow.setOnClickListener(this);
        positiveBlue.setOnClickListener(this);
        positiveRed.setOnClickListener(this);
        negative.setOnClickListener(this);


        builder.setView(viewDialog);
        dialogConfirmationCardSelection = builder.create();

        dialogConfirmationCardSelection.setOnShowListener(arg0 -> {
            CardViewer card = getCardsInHandCurrentPlayer().get(numberSelectionCard);
            if (card.isAvailable()) {
                if (card.getCard().getId() == 13 || card.getCard().getId() == 14) {
                    positive.setVisibility(View.INVISIBLE);
                    positiveRed.setVisibility(View.VISIBLE);
                    positiveGreen.setVisibility(View.VISIBLE);
                    positiveYellow.setVisibility(View.VISIBLE);
                    positiveBlue.setVisibility(View.VISIBLE);
                } else {
                    positive.setVisibility(View.VISIBLE);
                    positiveRed.setVisibility(View.INVISIBLE);
                    positiveGreen.setVisibility(View.INVISIBLE);
                    positiveYellow.setVisibility(View.INVISIBLE);
                    positiveBlue.setVisibility(View.INVISIBLE);
                }
                viewDialog.findViewById(R.id.card).setBackground(card.getDrawable(this));
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (!isPressed) {
            isPressed = true;
            switch (v.getId()) {
                case R.id.positive:
                    onClickPositive();
                    break;
                case R.id.positiveRed:
                    getCardsInHandCurrentPlayer().get(numberSelectionCard).getCard().setColor(Color.RED);
                    onClickPositive();
                    break;
                case R.id.positiveGreen:
                    getCardsInHandCurrentPlayer().get(numberSelectionCard).getCard().setColor(Color.GREEN);
                    onClickPositive();
                    break;
                case R.id.positiveYellow:
                    getCardsInHandCurrentPlayer().get(numberSelectionCard).getCard().setColor(Color.YELLOW);
                    onClickPositive();
                    break;
                case R.id.positiveBlue:
                    getCardsInHandCurrentPlayer().get(numberSelectionCard).getCard().setColor(Color.BLUE);
                    onClickPositive();
                    break;
                case R.id.negative:
                    dialogConfirmationCardSelection.dismiss();
                    break;
            }
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }


    private void onClickPositive() {
        table.add(getCardsInHandCurrentPlayer().get(numberSelectionCard));
        cardOnTheTable.setBackground(getCardsInHandCurrentPlayer().get(numberSelectionCard).getDrawable(this));

        getCardsInHandCurrentPlayer().remove(numberSelectionCard);

//            if (getCardsInHandCurrentPlayer().size() != 0) {
//                cardsInHand.setBackground(cards.get(0).getDrawable(requireContext()));
//            }
        quantityCardsInHand.setText(String.valueOf(getCardsInHandCurrentPlayer().size()));

        afterSelectingCard(false);
        dialogConfirmationCardSelection.dismiss();
    }

    protected class Hand extends ArrayList<CardViewer> {
        @Override
        public CardViewer remove(int index) {
            CardViewer cardViewer = super.remove(index);
            cardAdapter.notifyItemRemoved(index);
            return cardViewer;
        }

        @Override
        public boolean add(CardViewer cardViewer) {
            boolean cardViewer1 = super.add(cardViewer);
            cardAdapter.notifyItemInserted(super.indexOf(cardViewer));
            return cardViewer1;
        }

        public void setAvailable(int index, boolean isAvailable) {
            if (isAvailable != super.get(index).isAvailable()) {
                super.get(index).setAvailable(isAvailable);
                cardAdapter.notifyItemChanged(index);
            }
        }
    }


    public class Human extends User<Hand> {
        public Human() {
            this.setCardsInHand(new Hand());
        }
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
        private final LayoutInflater inflater;

        CardAdapter(Activity context) {
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View viewInGroup = inflater.inflate(R.layout.card, parent, false);
            ViewHolder holder = new ViewHolder(viewInGroup);
            viewInGroup.setOnClickListener(view1 -> {
                int position = holder.getAdapterPosition();
                if (getCardsInHandCurrentPlayer().get(position).isAvailable()) {
                    numberSelectionCard = position;
                    dialogConfirmationCardSelection.show();
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CardViewer card = getCardsInHandCurrentPlayer().get(position);
            holder.card.setBackground(card.getDrawable(getBaseContext()));
            if (!card.isAvailable()) {
                holder.available.setVisibility(View.VISIBLE);
            } else {
                holder.available.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return getCardsInHandCurrentPlayer().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View card;
            final View available;

            ViewHolder(View view) {
                super(view);
                card = view.findViewById(R.id.card);
                available = view.findViewById(R.id.not_available);
            }
        }
    }
}
