package msg;

public enum KindM {
    CONNECTION, 
    CONNECTIONACK,
    USERLIST,
    USERLISTACK,
    SENDFILE,
    SENDFILEACK,
    FILEREQUEST,
    FILEREQUESTACK,
    CLIENTSERVERREADY,
    SERVERCLIENTREADY,
    CLOSE,
    CLOSEACK,
    FILERECEIVED,
    FILERECEIVEDACK;
}
