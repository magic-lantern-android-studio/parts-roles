package com.wizzer.mle.parts.roles;

import com.wizzer.mle.math.MlTransform;
import com.wizzer.mle.math.MlVector3;
import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.runtime.core.MleRuntimeException;
import com.wizzer.mle.min3d.vos.Number3d;

/**
 * This class implements a carrier for propagating transform properties
 * between Actors and Roles.
 *
 * @see Mle3dRole
 */
public class Mle3dTransformCarrier
{
    /**
     * Set the transform from the specified MlTransform and update
     * the specified Role.
     *
     * @param role The Role to update.
     * @param transform The Transform to obtain the value from.
     *
     * @return If the Transform is successfully set on the Role, then
     * <b>true</b> will be returned. Otherwise, <b>false</b> will
     * be returned.
     *
     * @throws MleRuntimeException This exception is thrown if
     * the specified parameters are <b>null</b>. It will also be
     * thrown if an error occurs while setting the property.
     */
    public static final boolean set(IMleRole role, MlTransform transform)
            throws MleRuntimeException

    {
        I3dRole role3d;
        boolean retValue;

        if ((role != null) && (role instanceof I3dRole)) {
            role3d = (I3dRole) role;
        } else {
            throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
        }

        retValue = role3d.setTransform(transform);

        return retValue;
    }

    /**
     * Get the transform from the specified Role.
     *
     * @param role The Role to retrieve the transform from.
     * @param transform The Transform to update.
     *
     * @return If the Transform is successfully retrieved from the Role, then
     * <b>true</b> will be returned. Otherwise, <b>false</b> will
     * be returned.
     *
     * @throws MleRuntimeException This exception is thrown if
     * the specified parameters are <b>null</b>. It will also be
     * thrown if an error occurs while setting the property.
     */
    public static final boolean get(IMleRole role, MlTransform transform)
            throws MleRuntimeException
    {
        I3dRole role3d;
        boolean retValue;

        if ((role != null) && (role instanceof I3dRole)) {
            role3d = (I3dRole) role;
        } else {
            throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
        }

        retValue = role3d.getTransform(transform);

        return retValue;
    }
}
