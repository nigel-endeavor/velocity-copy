package com.endeavorms.velocity.qto.common.util;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/** A utility class to assist with working with files. */
public final class FileUtils {

    /** The Logger for this utility class. */
    private static final Logger OUT = LoggerFactory.getLogger(FileUtils.class);
    /** Constant used for the breaking up a file when reading/writing to it. */
    private static final int CHUNK_SIZE = 512;

    /** Private utility class constructor. */
    private FileUtils() {
    }

    /**
     * Returns an input stream created from the supplied connection String.
     * @param connection the connection String to the file used to create the InputStream.
     * @return a valid InputStream.
     * @throws IOException if there if a failure opening an InputStream with the supplied connection String.
     */
    public static InputStream getInputStream(final String connection) throws IOException {

        FileObject file = getFileObject(connection);

        FileContent content = file.getContent();

        return content.getInputStream();
    }

    /**
     * Returns a FileObject using a given connection String.
     * @param connection the connection String to the file used to create the FileObject.
     * @return an apache.commons.vfs.FileObject.
     * @throws IOException if there if a failure opening an FileObject with the supplied connection String.
     */
    public static FileObject getFileObject(final String connection) throws IOException {

        try {
            FileSystemManager fsManager = VFS.getManager();

            return fsManager.resolveFile(connection);

        } catch (FileSystemException e) {

            throw new IOException("can't open " + connection + ": " + e.getMessage());
        }
    }

    /**
     * Returns an InputStream for a given FileObject.
     * @param file the FileObject to get an InputStream for.
     * @return an InputStream for the given FileObject.
     * @throws IOException if there is a failure reading the file.
     */
    public static InputStream getInputStream(final FileObject file) throws IOException {

        FileContent content = file.getContent();

        return content.getInputStream();
    }

    /**
     * Returns a reader for the given FileObject.
     * @param file the FileObject to get a Reader for.
     * @return an Reader for the given FileObject.
     * @throws IOException if there is a failure reading the file.
     */
    public static Reader getReader(final FileObject file) throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream(file)));
    }

    /**
     * Returns a reader for the given file connection String.
     * @param connection the file connection String to get a Reader for.
     * @return an Reader for the given FileObject.
     * @throws IOException if there is a failure reading the file.
     */
    public static Reader getReader(final String connection) throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream(connection)));
    }

    /**
     * Returns a writer for the given file connection String.
     * @param connection the file connection String to get a Writer for.
     * @return an Writer for the given FileObject.
     * @throws IOException if there is a failure reading the file.
     */
    public static Writer getWriter(final String connection) throws IOException {

        return new FileWriter(connection);
    }

    /**
     * Returns whether the file, indicated by the fileUrl, has a mime type that is included in the acceptable mime types
     * in mimeTypes.
     * @param fileUrl the location of the file to validate.
     * @param mimeTypes the acceptable mime types to validate against.
     * @return a boolean indicating the validity of the file's mime type.
     * @throws IOException if there is a failure reading the file.
     */
    public static boolean isMimeValidType(final String fileUrl, final List<String> mimeTypes) throws IOException {
        boolean isMimeType = false;

        String mimeType = getMimeType(fileUrl);

        if (mimeTypes.contains(mimeType)) {
            isMimeType = true;
        }

        return isMimeType;
    }

    /**
     * Returns the mime type of a provided file.
     * @param fileUrl the location of the file.
     * @return the mime type of the file.
     * @throws IOException if there is a failure reading the file.
     */
    public static String getMimeType(final String fileUrl) throws IOException {

        FileObject file = getFileObject(fileUrl);

        return new Tika().detect(file.getURL());
    }

    /**
     * Please note that this method isn't as accurate as the other getMimeType() methods. Without hints from the meta
     * data provided by the file name of the file it only goes so far. Use the version that takes an input stream and
     * file name if you have the file name.
     * @param inputStream is an in initialized input stream
     * @return the MIME type of the input stream
     * @throws IOException when a read exception occurs
     */
    public static String getMimeType(final InputStream inputStream) throws IOException {

        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);

        return new Tika().detect(tikaInputStream);
    }

    /**
     * This is the most accurate method of detecting the MIME type based on an input stream.  It relies on the file name
     * and extension to provide hints to the detector.
     * @param fileName is the name of the file, can include full path or not
     * @param inputStream is an InputStream containing your data
     * @return the MIME type of the input stream
     * @throws IOException when a read exception occurs
     */
    public static String getMimeType(final String fileName, final InputStream inputStream) throws IOException {

        /* Tries to provide some more information to Tika to allow it to make the
         * best decision.  Doesn't really help.  Also tried with the container
         * aware detector from Tika.  That didn't work for input streams (0.9).
         * Need to revisit this to improve the algorithm.
         */
        Metadata metadata = new Metadata();
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);

        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);

        return new Tika().detect(tikaInputStream, metadata);
    }

    /**
     * Returns the appropriate mime type based on the file's name.
     * @param fileName the file name to use for determining the mime type.
     * @return a String representing the mime type.
     * @throws IOException if unable to access the fil
     */
    public static String getMimeTypeByFileName(final String fileName) throws IOException {

        Metadata metadata = new Metadata();
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);

        return new Tika().detect(null, metadata);
    }

    /**
     * Close a Reader and suppress any exceptions.  Errors are logged using log4J.
     * @param reader is an open instance of java.io.Reader
     */
    public static void closeReader(final Reader reader) {

        if (reader == null) {
            return;
        }

        try {
            reader.close();

        } catch (IOException ioe) {

            OUT.error("IO exception closing reader: " + ioe.toString());
        }
    }

    /**
     * Closes the supplied InputStream quietly.
     * @param stream the InputStream to close.
     */
    public static void closeInputStream(final InputStream stream) {

        if (stream == null) {
            return;
        }

        try {
            stream.close();

        } catch (IOException ioe) {

            OUT.error("IO exception closing stream: " + ioe.toString());
        }
    }

    /**
     * Coverts a file, via a given connection String, to an in memory String.
     * @param connection the connection String for the file to convert.
     * @return String representing the file.
     * @throws IOException if there is a failure reading the file.
     */
    public static String convertFileToString(final String connection) throws IOException {
        try {
            InputStream is = FileUtils.getInputStream(connection);

            return convertStreamToString(is);

        } catch (FileSystemException e) {

            throw new IOException("can't open " + connection + ": " + e.getMessage());
        }
    }

    /**
     * Coverts a file, via a given InputStream, to an in memory String.
     * @param is the InputStream for the file to convert.
     * @return String representing the file.
     * @throws IOException if there is a failure reading the file.
     */
    public static String convertStreamToString(final InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader returns null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(is, "UTF-8"));

                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();

        } else {

            return "";
        }
    }

    /**
     * Converts a file, via a given connection String, to Bytes.
     * @param connection the connection String for the file to convert.
     * @return a byte array representing the file.
     * @throws IOException if there is a failure reading the file.
     */
    public static byte[] convertFileToBytes(final String connection) throws IOException {

        try {
            InputStream is = FileUtils.getInputStream(connection);

            return convertStreamToBytes(is);

        } catch (FileSystemException e) {

            throw new IOException("can't open " + connection + ": " + e.getMessage());
        }
    }

    /**
     * Converts an InputStream to a byte array, broken into chunks the size of CHUNK_SIZE.
     * @param stream the InputStream to break up.
     * @return a byte array representing the InputStream.
     * @throws IOException if there is a failure reading the file.
     */
    public static byte[] convertStreamToBytes(final InputStream stream) throws IOException {

        int bytesRead = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buff = new byte[CHUNK_SIZE];
        while (-1 != (bytesRead = stream.read(buff))) {
            out.write(buff, 0, bytesRead);
        }

        return out.toByteArray();
    }

    /**
     * Gives the base name of a file - its name without the path prefix.
     * @param fileName the full file name including path
     * @return the base name of the file
     */
    public static String getBaseName(final String fileName) {

        File file = new File(fileName);
        return file.getName();
    }

    /**
     * Gives the base name of a file - its name without the path prefix. Will also remove the extension (everything
     * after the last dot).
     * @param fileName the full file name including path
     * @return the base name of the file
     */
    public static String getBaseNameWithoutExtension(final String fileName) {

        String baseName = getBaseName(fileName);

        int extensionPosition = getExtensionPosition(baseName);

        return baseName.substring(0, extensionPosition - 1);
    }

    /**
     * Returns a file's extension - everything after the last dot in the file name.
     * @param fileName the filename with extension
     * @return the extension of the file, no dot, null on failure
     */
    public static String getExtension(final String fileName) {

        String baseName = getBaseName(fileName);

        int extensionPosition = getExtensionPosition(baseName);

        return baseName.substring(extensionPosition, baseName.length());
    }

    /**
     * Finds the location of the extension in a file name.
     * @param fileName the file name to find the extension in.
     * @return the position of the extension in the fileName String.
     */
    private static int getExtensionPosition(final String fileName) {

        int length = fileName.length() - 1;

        char ch;
        do {
            ch = fileName.charAt(--length);
        } while (ch != '.');

        return length + 1;
    }
}