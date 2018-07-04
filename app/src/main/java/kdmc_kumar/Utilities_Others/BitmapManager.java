/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdmc_kumar.Utilities_Others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * This class provides several utilities to cancel bitmap decoding.
 * <p/>
 * The function decodeFileDescriptor() is used to decode a bitmap. During
 * decoding if another thread wants to cancel it, it calls the function
 * cancelThreadDecoding() specifying the Thread which is in decoding.
 * <p/>
 * cancelThreadDecoding() is sticky until allowThreadDecoding() is called.
 * <p/>
 * You can also cancel decoding for a set of threads using ThreadSet as
 * the parameter for cancelThreadDecoding. To put a thread into a ThreadSet,
 * use the add() method. A ThreadSet holds (weak) references to the threads,
 * so you don't need to remove Thread from it if some thread dies.
 */
public class BitmapManager {

    private static final String TAG = "BitmapManager";
    private static BitmapManager sManager = null;
    private final WeakHashMap<Thread, ThreadStatus> mThreadStatus =
            new WeakHashMap<>();

    private BitmapManager() {

    }

    public static BitmapManager instance() {
        synchronized (BitmapManager.class) {

            if (sManager == null) {
                sManager = new BitmapManager();
            }
            return sManager;
        }
    }

    /**
     * Get thread status and create one if specified.
     */
    private ThreadStatus getOrCreateThreadStatus(Thread t) {
        synchronized (this) {

            ThreadStatus status = mThreadStatus.get(t);
            if (status == null) {
                status = new ThreadStatus();
                mThreadStatus.put(t, status);
            }
            return status;
        }
    }

    /**
     * The following three methods are used to keep track of
     * BitmapFaction.Options used for decoding and cancelling.
     */
    private void setDecodingOptions(Thread t,
                                    BitmapFactory.Options options) {
        synchronized (this) {

            getOrCreateThreadStatus(t).mOptions = options;
        }
    }

    @Nullable
    final BitmapFactory.Options getDecodingOptions(Thread t) {
        synchronized (this) {

            ThreadStatus status = mThreadStatus.get(t);
            return status != null ? status.mOptions : null;
        }
    }

    private final void removeDecodingOptions(Thread t) {
        synchronized (this) {

            ThreadStatus status = mThreadStatus.get(t);
            status.mOptions = null;
        }
    }

    /**
     * The following two methods are used to allow/cancel a set of threads
     * for bitmap decoding.
     */
    public final void allowThreadDecoding(ThreadSet threads) {
        synchronized (this) {

            for (Iterator<Thread> iterator = threads.iterator(); iterator.hasNext(); ) {
                Thread t = iterator.next();
                allowThreadDecoding(t);
            }
        }
    }

    public final void cancelThreadDecoding(ThreadSet threads) {
        synchronized (this) {

            for (Iterator<Thread> iterator = threads.iterator(); iterator.hasNext(); ) {
                Thread t = iterator.next();
                cancelThreadDecoding(t);
            }
        }
    }

    /**
     * The following three methods are used to keep track of which thread
     * is being disabled for bitmap decoding.
     */
    private final boolean canThreadDecoding(Thread t) {
        synchronized (this) {

            ThreadStatus status = mThreadStatus.get(t);
            if (status == null) {
                // allow decoding by default
                return true;
            }

            return (status.mState != State.CANCEL);
        }
    }

    private final void allowThreadDecoding(Thread t) {
        synchronized (this) {

            getOrCreateThreadStatus(t).mState = State.ALLOW;
        }
    }

    private final void cancelThreadDecoding(Thread t) {
        synchronized (this) {

            ThreadStatus status = getOrCreateThreadStatus(t);
            status.mState = State.CANCEL;
            if (status.mOptions != null) {
                status.mOptions.requestCancelDecode();
            }

            // Wake up threads in waiting list
            notifyAll();
        }
    }

    /**
     * A debugging routine.
     */
    public final void dump() {
        synchronized (this) {

            for (Map.Entry<Thread, ThreadStatus> entry : mThreadStatus.entrySet()) {
                Log.v(TAG, "[Dump] Thread " + entry.getKey() + " ("
                        + entry.getKey().getId()
                        + ")'s status is " + entry.getValue());
            }
        }
    }

    /**
     * The real place to delegate bitmap decoding to BitmapFactory.
     */
    @Nullable
    public final Bitmap decodeFileDescriptor(FileDescriptor fd,
                                             BitmapFactory.Options options) {

        if (options.mCancel) {
            return null;
        }

        Thread thread = Thread.currentThread();
        if (!canThreadDecoding(thread)) {
            // Log.d(TAG, "Thread " + thread + " is not allowed to decode.");
            return null;
        }

        setDecodingOptions(thread, options);
        Bitmap b = BitmapFactory.decodeFileDescriptor(fd, null, options);

        removeDecodingOptions(thread);
        return b;
    }

    private enum State {CANCEL, ALLOW}

    private static class ThreadStatus {

        State mState = State.ALLOW;
        @Nullable
        BitmapFactory.Options mOptions = null;

        private ThreadStatus() {
        }

        @Override
        public final String toString() {

            String s;
            switch (mState) {
                case CANCEL:
                    s = "Cancel";
                    break;
                case ALLOW:
                    s = "Allow";
                    break;
                default:
                    s = "?";
                    break;
            }
            s = "thread state = " + s + ", options = " + mOptions;
            return s;
        }
    }

    public static class ThreadSet implements Iterable<Thread> {

        private final WeakHashMap<Thread, Object> mWeakCollection =
                new WeakHashMap<>();

        public ThreadSet() {
        }

        public final void add(Thread t) {

            mWeakCollection.put(t, null);
        }

        public final void remove(Thread t) {

            mWeakCollection.remove(t);
        }

        @NonNull
        public final Iterator<Thread> iterator() {

            return mWeakCollection.keySet().iterator();
        }
    }
}
