package vn.edu.hcmut.linexo.presentation.view_model.play;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.BR;
import vn.edu.hcmut.linexo.domain.interactor.PlayUsecase;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.model.Board;
import vn.edu.hcmut.linexo.presentation.model.Message;
import vn.edu.hcmut.linexo.presentation.model.Room;
import vn.edu.hcmut.linexo.presentation.model.User;
import vn.edu.hcmut.linexo.presentation.view.play.ChatRecyclerViewAdapter;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModelCallback;
import vn.edu.hcmut.linexo.utils.Event;
import vn.edu.hcmut.linexo.utils.NetworkChangeReceiver;

public class PlayViewModel extends BaseObservable implements ViewModel, ViewModelCallback {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    private Usecase playUsecase;

    private Room room;
    private int roomId;
    private ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(new ArrayList<>());
    private List<Message> messages;
    private User user;
    private boolean isConnected;
    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    int[] arrayKeyboardChanged = {0, 0};
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    int j = 0;

    public PlayViewModel(Context context, Usecase playUsecase) {
        this.playUsecase = playUsecase;

        networkChangeReceiver.initReceiver(context, new NetworkChangeReceiver.NetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean networkState) {
                isConnected = networkState;
                notifyPropertyChanged(BR.networkVisibility);
            }
        });

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            user = new User(firebaseUser.getUid(), firebaseUser.getEmail(),
                    firebaseUser.getPhotoUrl().toString(), firebaseUser.getDisplayName(), 0, System.currentTimeMillis());
//            onHelp(Event.create(Event.LOGIN_USER, user));
        }

        messages = new ArrayList<>();
        messages.add(new Message(1, j++ + "", null, null, "alo"));
        messages.add(new Message(2, j++ + "", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(2, j++ + "", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(3, j++ + "", null, null, "Lâm Nguyễn đang theo dõi"));
        messages.add(new Message(1, j++ + "", null, null, "111"));
        messages.add(new Message(1, j++ + "", null, null, "đường nào mày...... ha ha ha chết chưa m hả bưởi."));
        messages.add(new Message(3, j++ + "", null, null, "Lâm Nguyễn đã thoát"));
        adapter = new ChatRecyclerViewAdapter(messages);
        messages = new ArrayList<>(messages);
    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {
        publisher.subscribe(observer);
        if (room == null) {
            loadRoom();
        }
    }

    @Override
    public void onHelp(Event e) {
        switch (e.getType()) {
            case Event.LOAD_MESSAGE: {
                messages = new ArrayList<>((List<Message>) e.getData()[0]);
                adapter.updateMessageListItems(messages);
                break;
            }
            case Event.LOAD_PLAY_INFO:
                // load avatar url host and opponent, score by room ID
                // check url null

                roomId = (int) e.getData()[0];


                notifyPropertyChanged(BR.roomId);
                break;
            case Event.KEYBOARD_CHANGED: {
                arrayKeyboardChanged = (int[]) e.getData()[0];
                notifyPropertyChanged(BR.arrayKeyboardChanged);
                break;
            }
        }
    }

    @Bindable
    public Board getBoard() {
        if (room == null)
            return null;
        return room.getBoard();
    }

    @Bindable
    public int[] getTouch() {
        return null;
    }

    public void setTouch(int[] touch) {
        if (PlayViewModel.this.room.getRoom_number() == 0) {
            if (!PlayViewModel.this.room.getNext_turn().equals("AI"))
                playUsecase.execute(new RoomRecieverObserver(),
                        Event.SEND_MOVE,
                        touch[0], touch[1]
                );
        } else if (PlayViewModel.this.room.getNext_turn().equals(user.getUid())) {
            playUsecase.execute(new RoomRecieverObserver(),
                    Event.SEND_MOVE,
                    touch[0], touch[1]
            );
        }
    }

    private void loadRoom() {
        playUsecase.execute(
                new DisposableSingleObserver<Room>() {
                    @Override
                    public void onSuccess(Room room) {
                        PlayViewModel.this.room = room;
                        notifyPropertyChanged(BR.board);
                        //TODO: count down time
                        if (PlayViewModel.this.room.getRoom_number() == 0) {
                            if (PlayViewModel.this.room.getNext_turn().equals("AI"))
                                getOpponentMove();
                        } else if (!PlayViewModel.this.room.getNext_turn().equals(user.getUid())) {
                            getOpponentMove();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                Event.INIT_GAME,
                roomId
        );
    }

    private void getOpponentMove() {
        playUsecase.execute(
                new DisposableSingleObserver<Room>() {
                    @Override
                    public void onSuccess(Room room) {
//                        PlayViewModel.this.room.setBoard(room.getBoard());
                        PlayViewModel.this.room = room;
                        notifyPropertyChanged(BR.board);
                        if (PlayViewModel.this.room.getRoom_number() == 0) {
                            if (PlayViewModel.this.room.getNext_turn().equals("AI"))
                                getOpponentMove();
                        } else if (!PlayViewModel.this.room.getNext_turn().equals(user.getUid())) {
                            getOpponentMove();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                },
                Event.GET_MOVE,
                null
        );
    }

    public void onClickSend(View view) {
        messages = new ArrayList<>(messages);

        messages.add(new Message(2, j++ + "", "khuong tu nha", "https://i.pinimg.com/originals/30/60/5a/30605a36231a5b7cd5ad0af4ee6774e3.jpg", "đi đường kia kìa :)"));
        messages.add(new Message(3, j++ + "", null, null, "Lâm Nguyễn đang theo dõi"));
        messages.add(new Message(1, j++ + "", null, null, "đường nào mày...... ha ha ha chết chưa m hả bưởi."));

        onHelp(Event.create(Event.LOAD_MESSAGE, messages));
    }

    @Bindable
    public ChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Bindable
    public int getNetworkVisibility() {
        if (isConnected) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Bindable
    public int getRoomId() {
        return roomId;
    }

    @Bindable
    public int[] getArrayKeyboardChanged() {
        return arrayKeyboardChanged;
    }

    @Override
    public void endTask() {
        playUsecase.endTask();
    }

    class RoomRecieverObserver extends DisposableSingleObserver<Room> {

        @Override
        public void onSuccess(Room room) {
            PlayViewModel.this.room = room;
            notifyPropertyChanged(BR.board);
            //TODO: count down time
            if (PlayViewModel.this.room.getRoom_number() == 0) {
                if (PlayViewModel.this.room.getNext_turn().equals("AI"))
                    getOpponentMove();
            } else if (user != null && !PlayViewModel.this.room.getNext_turn().equals(user.getUid())) {
                getOpponentMove();
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
