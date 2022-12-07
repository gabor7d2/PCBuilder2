package net.gabor7d2.pcbuilder.repositoryimpl;

import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repository.ProgressListener;
import net.gabor7d2.pcbuilder.repository.ProfileImporterRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class JsonProfileImporterRepository implements ProfileImporterRepository {

    private final JsonProfileRepository jsonProfileRepository = new JsonProfileRepository();

    @Override
    public void importFromZipFile(File zipFile,
                                  ProgressListener<ImportResultCode, Collection<Profile>> progressListener,
                                  AtomicBoolean cancellationToken) {
        ZipExtractorThread thread = new ZipExtractorThread(jsonProfileRepository.profilesDirectory, zipFile, new ProgressListener<>() {
            @Override
            public void preparing(int maxProgress) {
                progressListener.preparing(maxProgress);
            }

            @Override
            public void starting() {
                progressListener.starting();
            }

            @Override
            public void progress(int progress) {
                progressListener.progress(progress);
            }

            @Override
            public void cancelling() {
                progressListener.cancelling();
            }

            @Override
            public void completed(ImportResultCode resultCode, Collection<File> extractedFiles) {
                if (resultCode == ImportResultCode.SUCCESS) {
                    progressListener.progress(Integer.MAX_VALUE);
                    progressListener.completed(ImportResultCode.SUCCESS,
                            jsonProfileRepository.loadProfiles(getProfileDirsFromExtractedFiles(extractedFiles)));
                } else {
                    progressListener.completed(resultCode, null);
                }
            }
        }, cancellationToken);
        thread.start();
    }

    private Collection<File> getProfileDirsFromExtractedFiles(Collection<File> extractedFiles) {
        List<File> profileDirs = new ArrayList<>();

        for (File f : extractedFiles) {
            try {
                if (f.getParentFile().getCanonicalPath().equals(jsonProfileRepository.profilesDirectory.getCanonicalPath())) {
                    profileDirs.add(f);
                }
            } catch (IOException e) {
                new RuntimeException("Failed to load profile dir.", e).printStackTrace();
            }
        }

        return profileDirs;
    }
}
