// Generated from Dependencies.g4 by ANTLR 4.3
package org.okbqa.tripletempeh.interpreter.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DependenciesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		NUMBER=10, STRING=11, NEWLINE=12, WHITESPACE=13;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "','", "'_'", "'-'", "'('", "':'", "'='", "'|'", "';'", 
		"NUMBER", "STRING", "NEWLINE", "WHITESPACE"
	};
	public static final int
		RULE_graph = 0, RULE_conll = 1, RULE_stanford = 2, RULE_features = 3, 
		RULE_feature = 4, RULE_roles = 5, RULE_role = 6;
	public static final String[] ruleNames = {
		"graph", "conll", "stanford", "features", "feature", "roles", "role"
	};

	@Override
	public String getGrammarFileName() { return "Dependencies.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DependenciesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GraphContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(DependenciesParser.NEWLINE); }
		public List<StanfordContext> stanford() {
			return getRuleContexts(StanfordContext.class);
		}
		public TerminalNode NEWLINE(int i) {
			return getToken(DependenciesParser.NEWLINE, i);
		}
		public StanfordContext stanford(int i) {
			return getRuleContext(StanfordContext.class,i);
		}
		public ConllContext conll(int i) {
			return getRuleContext(ConllContext.class,i);
		}
		public List<ConllContext> conll() {
			return getRuleContexts(ConllContext.class);
		}
		public GraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graph; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterGraph(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitGraph(this);
		}
	}

	public final GraphContext graph() throws RecognitionException {
		GraphContext _localctx = new GraphContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_graph);
		int _la;
		try {
			setState(30);
			switch (_input.LA(1)) {
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(14); conll();
				setState(19);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NEWLINE) {
					{
					{
					setState(15); match(NEWLINE);
					setState(16); conll();
					}
					}
					setState(21);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(22); stanford();
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NEWLINE) {
					{
					{
					setState(23); match(NEWLINE);
					setState(24); stanford();
					}
					}
					setState(29);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConllContext extends ParserRuleContext {
		public RolesContext roles() {
			return getRuleContext(RolesContext.class,0);
		}
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public FeaturesContext features() {
			return getRuleContext(FeaturesContext.class,0);
		}
		public TerminalNode NUMBER(int i) {
			return getToken(DependenciesParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(DependenciesParser.NUMBER); }
		public List<TerminalNode> STRING() { return getTokens(DependenciesParser.STRING); }
		public ConllContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conll; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterConll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitConll(this);
		}
	}

	public final ConllContext conll() throws RecognitionException {
		ConllContext _localctx = new ConllContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_conll);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32); match(NUMBER);
			setState(33); match(STRING);
			setState(34); match(STRING);
			setState(35); match(STRING);
			setState(36); features();
			setState(37); match(NUMBER);
			setState(38); match(STRING);
			{
			setState(39); roles();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StanfordContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public TerminalNode NUMBER(int i) {
			return getToken(DependenciesParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(DependenciesParser.NUMBER); }
		public List<TerminalNode> STRING() { return getTokens(DependenciesParser.STRING); }
		public StanfordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stanford; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterStanford(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitStanford(this);
		}
	}

	public final StanfordContext stanford() throws RecognitionException {
		StanfordContext _localctx = new StanfordContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stanford);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41); match(STRING);
			setState(42); match(T__4);
			setState(43); match(STRING);
			setState(44); match(T__5);
			setState(45); match(NUMBER);
			setState(46); match(T__7);
			setState(47); match(STRING);
			setState(48); match(T__5);
			setState(49); match(NUMBER);
			setState(50); match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeaturesContext extends ParserRuleContext {
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public FeaturesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_features; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterFeatures(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitFeatures(this);
		}
	}

	public final FeaturesContext features() throws RecognitionException {
		FeaturesContext _localctx = new FeaturesContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_features);
		int _la;
		try {
			setState(61);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(52); match(T__6);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(53); feature();
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(54); match(T__1);
					setState(55); feature();
					}
					}
					setState(60);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public List<TerminalNode> STRING() { return getTokens(DependenciesParser.STRING); }
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterFeature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitFeature(this);
		}
	}

	public final FeatureContext feature() throws RecognitionException {
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_feature);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63); match(STRING);
			setState(64); match(T__2);
			setState(65); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RolesContext extends ParserRuleContext {
		public List<RoleContext> role() {
			return getRuleContexts(RoleContext.class);
		}
		public RoleContext role(int i) {
			return getRuleContext(RoleContext.class,i);
		}
		public RolesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_roles; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterRoles(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitRoles(this);
		}
	}

	public final RolesContext roles() throws RecognitionException {
		RolesContext _localctx = new RolesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_roles);
		int _la;
		try {
			setState(76);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(67); match(T__6);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(68); role();
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(69); match(T__0);
					setState(70); role();
					}
					}
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RoleContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public TerminalNode NUMBER() { return getToken(DependenciesParser.NUMBER, 0); }
		public List<TerminalNode> STRING() { return getTokens(DependenciesParser.STRING); }
		public RoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_role; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterRole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitRole(this);
		}
	}

	public final RoleContext role() throws RecognitionException {
		RoleContext _localctx = new RoleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_role);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78); match(NUMBER);
			setState(79); match(T__3);
			setState(80); match(STRING);
			setState(83);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(81); match(T__5);
				setState(82); match(STRING);
				}
			}

			setState(87);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(85); match(T__2);
				setState(86); match(STRING);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\17\\\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\7\2\24\n\2\f"+
		"\2\16\2\27\13\2\3\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\5\2!\n\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\7\5;\n\5\f\5\16\5>\13\5\5\5@\n\5\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\7\7J\n\7\f\7\16\7M\13\7\5\7O\n\7\3\b\3\b\3\b\3\b\3\b"+
		"\5\bV\n\b\3\b\3\b\5\bZ\n\b\3\b\2\2\t\2\4\6\b\n\f\16\2\2]\2 \3\2\2\2\4"+
		"\"\3\2\2\2\6+\3\2\2\2\b?\3\2\2\2\nA\3\2\2\2\fN\3\2\2\2\16P\3\2\2\2\20"+
		"\25\5\4\3\2\21\22\7\16\2\2\22\24\5\4\3\2\23\21\3\2\2\2\24\27\3\2\2\2\25"+
		"\23\3\2\2\2\25\26\3\2\2\2\26!\3\2\2\2\27\25\3\2\2\2\30\35\5\6\4\2\31\32"+
		"\7\16\2\2\32\34\5\6\4\2\33\31\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36"+
		"\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2 \20\3\2\2\2 \30\3\2\2\2!\3\3\2\2\2"+
		"\"#\7\f\2\2#$\7\r\2\2$%\7\r\2\2%&\7\r\2\2&\'\5\b\5\2\'(\7\f\2\2()\7\r"+
		"\2\2)*\5\f\7\2*\5\3\2\2\2+,\7\r\2\2,-\7\7\2\2-.\7\r\2\2./\7\6\2\2/\60"+
		"\7\f\2\2\60\61\7\4\2\2\61\62\7\r\2\2\62\63\7\6\2\2\63\64\7\f\2\2\64\65"+
		"\7\3\2\2\65\7\3\2\2\2\66@\7\5\2\2\67<\5\n\6\289\7\n\2\29;\5\n\6\2:8\3"+
		"\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=@\3\2\2\2><\3\2\2\2?\66\3\2\2\2?"+
		"\67\3\2\2\2@\t\3\2\2\2AB\7\r\2\2BC\7\t\2\2CD\7\r\2\2D\13\3\2\2\2EO\7\5"+
		"\2\2FK\5\16\b\2GH\7\13\2\2HJ\5\16\b\2IG\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL"+
		"\3\2\2\2LO\3\2\2\2MK\3\2\2\2NE\3\2\2\2NF\3\2\2\2O\r\3\2\2\2PQ\7\f\2\2"+
		"QR\7\b\2\2RU\7\r\2\2ST\7\6\2\2TV\7\r\2\2US\3\2\2\2UV\3\2\2\2VY\3\2\2\2"+
		"WX\7\t\2\2XZ\7\r\2\2YW\3\2\2\2YZ\3\2\2\2Z\17\3\2\2\2\13\25\35 <?KNUY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}