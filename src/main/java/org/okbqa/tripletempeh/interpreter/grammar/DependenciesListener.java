// Generated from Dependencies.g4 by ANTLR 4.3
package org.okbqa.tripletempeh.interpreter.grammar;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DependenciesParser}.
 */
public interface DependenciesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DependenciesParser#graph}.
	 * @param ctx the parse tree
	 */
	void enterGraph(@NotNull DependenciesParser.GraphContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#graph}.
	 * @param ctx the parse tree
	 */
	void exitGraph(@NotNull DependenciesParser.GraphContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#roles}.
	 * @param ctx the parse tree
	 */
	void enterRoles(@NotNull DependenciesParser.RolesContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#roles}.
	 * @param ctx the parse tree
	 */
	void exitRoles(@NotNull DependenciesParser.RolesContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#features}.
	 * @param ctx the parse tree
	 */
	void enterFeatures(@NotNull DependenciesParser.FeaturesContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#features}.
	 * @param ctx the parse tree
	 */
	void exitFeatures(@NotNull DependenciesParser.FeaturesContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#conll}.
	 * @param ctx the parse tree
	 */
	void enterConll(@NotNull DependenciesParser.ConllContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#conll}.
	 * @param ctx the parse tree
	 */
	void exitConll(@NotNull DependenciesParser.ConllContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#role}.
	 * @param ctx the parse tree
	 */
	void enterRole(@NotNull DependenciesParser.RoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#role}.
	 * @param ctx the parse tree
	 */
	void exitRole(@NotNull DependenciesParser.RoleContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterFeature(@NotNull DependenciesParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitFeature(@NotNull DependenciesParser.FeatureContext ctx);

	/**
	 * Enter a parse tree produced by {@link DependenciesParser#stanford}.
	 * @param ctx the parse tree
	 */
	void enterStanford(@NotNull DependenciesParser.StanfordContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependenciesParser#stanford}.
	 * @param ctx the parse tree
	 */
	void exitStanford(@NotNull DependenciesParser.StanfordContext ctx);
}