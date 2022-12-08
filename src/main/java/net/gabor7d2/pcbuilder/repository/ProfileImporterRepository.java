package net.gabor7d2.pcbuilder.repository;

import net.gabor7d2.pcbuilder.model.Profile;
import net.gabor7d2.pcbuilder.repositoryimpl.ImportResultCode;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ProfileImporterRepository {

    // TODO move to profilerepository
    void importFromZipFile(File zipFile,
                           ProgressListener<ImportResultCode, Collection<Profile>> progressListener,
                           AtomicBoolean cancellationToken);
}
