package com.wizzer.mle.parts.roles;

import java.util.Vector;

import com.wizzer.mle.math.MlTransform;
import com.wizzer.mle.math.MlVector3;
import com.wizzer.mle.min3d.vos.Number3d;
import com.wizzer.mle.parts.j3d.sets.I3dSet;
import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleActor;
import com.wizzer.mle.runtime.core.MleRole;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.min3d.Node;
import com.wizzer.mle.runtime.core.MleRuntimeException;

/**
 * Created by msm on 8/16/16.
 */
public class Mle3dRole extends MleRole implements I3dRole
{
    protected I3dNodeTypeProperty.NodeType m_nodeType;
    protected Node m_root;

    private Vector<IMleRole> m_children;

    public Mle3dRole(MleActor actor, I3dNodeTypeProperty.NodeType nodeType)
    {
        super(actor);

        if (nodeType == null)
            m_nodeType = I3dNodeTypeProperty.NodeType.NONE;
        else
            m_nodeType = nodeType;
        m_root = null;
        m_children = new Vector<>();
    }

    @Override
    public void init()
    {
        // Create a Node of type m_nodeType.
        m_root = new Node(m_nodeType, this);
    }

    @Override
    public void dispose()
    {
        if (m_root != null) {
            // Remove it from its parent.
            Node parent = m_root.getParent();
            if (parent != null) {
                parent.removeChild(m_root);
                m_root.setParent(null);
            }

            // Release Node resources;
            m_root.clear();

            // Remove reference back to Role.
            m_root.setRole(null);
        }
        m_nodeType = I3dNodeTypeProperty.NodeType.NONE;
        m_root = null;
    }

    @Override
    public void addChild(IMleRole child)
    {
        if ((child != null) && (child instanceof Mle3dRole))
            m_children.add(child);
        // ToDo: what happens if child is not a 3d Role?
    }

    public void removeChild(IMleRole child)
    {
        if ((child != null) && (child instanceof Mle3dRole)) {
            m_children.remove(child);
        }
    }

    public IMleRole getChildAt(int i)
    {
        return m_children.get(i);
    }

    public void clearChildren()
    {
        m_children.clear();
    }

    public int numChildren() { return m_children.size(); }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType)
    {
        m_nodeType = nodeType;
    }

    public I3dNodeTypeProperty.NodeType getNodeType() { return m_nodeType; }

    public void setRoot(Node root) { m_root = root; }

    public Node getRoot() { return m_root; }

    private float[] m_viewMatrix = new float[16];
    private float[] m_projectionMatrix = new float[16];

    public void setViewMatrix(float[] matrix)
    {
        for (int i = 0;  i < 16; i++)
            m_viewMatrix[i] = matrix[i];
    }

    public float[] getViewMatrix()
    { return m_viewMatrix; }

    public void setProjectionMatrix(float[] matrix)
    {
        for (int i = 0;  i < 16; i++)
            m_projectionMatrix[i] = matrix[i];
    }

    public float[] getProjectionMatrix()
    { return m_projectionMatrix; }

    @Override
    public void initRender()
        throws MleRuntimeException
    {
        setViewMatrix(((I3dSet)m_set).getViewMatrix());
        setProjectionMatrix(((I3dSet)m_set).getProjectionMatrix());

        // Initialize the associated Node.
        m_root.initRender();

        // Initialize the children nodes.
        for (int i = 0; i < numChildren(); i++)
        {
            IMleRole child = getChildAt(i);
            child.initRender();
            // ToDo: The scene graph must be comprised only of Mle3dRoles. In the future,
            // we may need to handle other rendering strategies.
        }
    }

    @Override
    public void render()
        throws MleRuntimeException
    {
        // Render the associated Node.
        m_root.render(m_viewMatrix, m_projectionMatrix);

        // Render children nodes.
        for (int i = 0; i < numChildren(); i++)
        {
            IMleRole child = getChildAt(i);
            child.render();
            // ToDo: The scene graph must be comprised only of Mle3dRoles. In the future,
            // we may need to handle other rendering strategies.
        }
    }

    @Override
    public boolean setTransform(MlTransform transform)
    {
        boolean retValue = false;

        if ((m_root != null) && (transform != null))
        {
            // XXX - limiting to TRANSFORM? This is where model may be breaking!
            if (m_root.getNodeType() == I3dNodeTypeProperty.NodeType.TRANSFORM)
            {
                // Set the transform on the Node.
                Number3d position = m_root.position();
                Number3d rotation = m_root.rotation();
                Number3d scale = m_root.scale();

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
        }

        return retValue;
    }

    @Override
    public boolean getTransform(MlTransform transform)
    {
        boolean retValue = false;

        if ((m_root != null) && (transform != null))
        {
            if (m_root.getNodeType() == I3dNodeTypeProperty.NodeType.TRANSFORM)
            {
                // Get the transform from the Node.
                Number3d position = m_root.position();
                Number3d rotation = m_root.rotation();
                Number3d scale = m_root.scale();

                MlVector3 tTranslation = new MlVector3(position.x, position.y, position.z);
                MlVector3 tRotation = new MlVector3(rotation.x, rotation.y, rotation.z);
                MlVector3 tScale = new MlVector3(scale.x, scale.y, scale.z);
                transform.setTransform(tTranslation, tRotation, tScale);

                retValue = true;
            }
        }

        return retValue;
    }
}
