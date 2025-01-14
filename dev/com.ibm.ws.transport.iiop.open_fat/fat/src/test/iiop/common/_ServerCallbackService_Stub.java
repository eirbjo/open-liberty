// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package test.iiop.common;

import java.lang.String;
import java.lang.Throwable;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;

public class _ServerCallbackService_Stub extends Stub implements ServerCallbackService {

    private static final String[] _type_ids = {
        "RMI:test.iiop.common.ServerCallbackService:0000000000000000"
    };

    public String[] _ids() {
        return (String [] )  _type_ids.clone();
    }

    public void throwRuntimeException(ClientCallbackService arg0) throws RemoteException {
        while(true) {
            if (!Util.isLocal(this)) {
                InputStream in = null;
                try {
                    try {
                        OutputStream out = _request("throwRuntimeException", true);
                        Util.writeRemoteObject(out,arg0);
                        _invoke(out);
                        return;
                    } catch (ApplicationException ex) {
                        in = ex.getInputStream();
                        String id = in.read_string();
                        throw new UnexpectedException(id);
                    } catch (RemarshalException ex) {
                        continue;
                    }
                } catch (SystemException ex) {
                    throw Util.mapSystemException(ex);
                } finally {
                    _releaseReply(in);
                }
            } else {
                ServantObject so = _servant_preinvoke("throwRuntimeException",test.iiop.common.ServerCallbackService.class);
                if (so == null) {
                    continue;
                }
                try {
                    ClientCallbackService arg0Copy = (ClientCallbackService) Util.copyObject(arg0,_orb());
                    ((test.iiop.common.ServerCallbackService)so.servant).throwRuntimeException(arg0Copy);
                    return;
                } catch (Throwable ex) {
                    Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                    throw Util.wrapException(exCopy);
                } finally {
                    _servant_postinvoke(so);
                }
            }
        }
    }
}
