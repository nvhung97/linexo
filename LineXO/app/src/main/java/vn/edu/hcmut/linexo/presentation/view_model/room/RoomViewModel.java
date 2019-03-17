package vn.edu.hcmut.linexo.presentation.view_model.room;

import android.databinding.BaseObservable;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.utils.Event;

public class RoomViewModel extends BaseObservable implements ViewModel {

    /**
     * Publisher will emit event to view. View listen these event via a observer.
     */
    private PublishSubject<Event> publisher = PublishSubject.create();

    public RoomViewModel() {

    }

    @Override
    public void subscribeObserver(Observer<Event> observer) {

    }

    @Override
    public void endTask() {

    }
}