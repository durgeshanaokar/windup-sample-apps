package org.jboss.windup.addon.config.operation.ruleelement;

import org.apache.commons.lang.StringUtils;
import org.jboss.windup.addon.config.GraphRewrite;
import org.jboss.windup.addon.config.selectables.SelectionFactory;
import org.jboss.windup.graph.GraphContext;
import org.jboss.windup.graph.GraphUtil;
import org.jboss.windup.graph.model.ArchiveModel;
import org.jboss.windup.graph.model.meta.WindupVertexFrame;
import org.jboss.windup.graph.model.resource.EarArchiveModel;
import org.jboss.windup.graph.model.resource.JarArchiveModel;
import org.jboss.windup.graph.model.resource.WarArchiveModel;
import org.ocpsoft.rewrite.context.EvaluationContext;

public class ArchiveTypingRuleElement extends AbstractIterationOperator<ArchiveModel>
{
    public ArchiveTypingRuleElement(String variableName)
    {
        super(ArchiveModel.class, variableName);
    }

    public static ArchiveTypingRuleElement addTypes(String variableName)
    {
        return new ArchiveTypingRuleElement(variableName);
    }

    @Override
    public void perform(GraphRewrite event, EvaluationContext context, ArchiveModel archiveModel)
    {
        GraphContext graphContext = event.getGraphContext();
        String filename = archiveModel.getArchiveName();
        WindupVertexFrame newFrame = null;
        if (StringUtils.endsWith(filename, ".jar"))
        {
            newFrame = GraphUtil.addTypeToModel(graphContext, archiveModel, JarArchiveModel.class);
        }
        else if (StringUtils.endsWith(filename, ".war"))
        {
            newFrame = GraphUtil.addTypeToModel(graphContext, archiveModel, WarArchiveModel.class);
        }
        else if (StringUtils.endsWith(filename, ".ear"))
        {
            newFrame = GraphUtil.addTypeToModel(graphContext, archiveModel, EarArchiveModel.class);
        }
        if (newFrame != null)
        {
            SelectionFactory.instance(event).setCurrentPayload(getVariableName(), newFrame);
        }
    }
}
