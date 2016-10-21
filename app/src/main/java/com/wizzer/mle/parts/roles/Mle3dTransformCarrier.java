package com.wizzer.mle.parts.roles;

import com.wizzer.mle.math.MlTransform;
import com.wizzer.mle.math.MlVector3;
import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
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
        Node root = null;
        boolean retValue = false;

        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
        }

        if (root != null) {
            if (transform != null) {
                if (root.getNodeType() == I3dNodeTypeProperty.NodeType.TRANSFORM) {
                    // Set the transform on the Node.
                    Number3d position = root.position();
                    Number3d rotation = root.rotation();
                    Number3d scale = root.scale();

                    float[] tTranslation = new float[3];
                    transform.getTranslation(tTranslation);
                    float[] tRotation = new float[3];
                    transform.getRotation(tRotation);
                    float[] tScale = new float[3];
                    transform.getScale(tScale);

                    position.setAll(tTranslation[0], tTranslation[1], tTranslation[2]);
                    rotation.setAll(tRotation[0], tRotation[1], tRotation[2]);
                    scale.setAll(tScale[0], tScale[1], tScale[2]);

                    retValue = true;
                }
            } else {
                throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
            }
        }

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
        Node root = null;
        boolean retValue = false;

        if ((role != null) && (role instanceof Mle3dRole)) {
            root = ((Mle3dRole) role).getRoot();
        } else {
            throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
        }

        if (root != null) {
            if (transform != null) {
                if (root.getNodeType() == I3dNodeTypeProperty.NodeType.TRANSFORM) {
                    // Get the transform from the Node.
                    Number3d position = root.position();
                    Number3d rotation = root.rotation();
                    Number3d scale = root.scale();

                    MlVector3 tTranslation = new MlVector3(position.x, position.y, position.z);
                    MlVector3 tRotation = new MlVector3(rotation.x, rotation.y, rotation.z);
                    MlVector3 tScale = new MlVector3(scale.x, scale.y, scale.z);
                    transform.setTransform(tTranslation, tRotation, tScale);

                    retValue = true;
                }
            } else {
                throw new MleRuntimeException("Mle3dTransformCarrier: Invalid input arguments.");
            }
        }

        return retValue;
    }
}
