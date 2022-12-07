package net.gabor7d2.pcbuilder.repository;

public interface ProgressListener<RC, R> {

    void preparing(int maxProgress);

    void starting();

    void progress(int currentProgress);

    void cancelling();

    void completed(RC resultCode, R result);
}