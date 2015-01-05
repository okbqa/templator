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
		RULE_graph = 0, RULE_conll = 1, RULE_stanford = 2, RULE_id = 3, RULE_features = 4, 
		RULE_feature = 5, RULE_sheads = 6, RULE_shead = 7;
	public static final String[] ruleNames = {
		"graph", "conll", "stanford", "id", "features", "feature", "sheads", "shead"
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
			setState(32);
			switch (_input.LA(1)) {
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(16); conll();
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NEWLINE) {
					{
					{
					setState(17); match(NEWLINE);
					setState(18); conll();
					}
					}
					setState(23);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(24); stanford();
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NEWLINE) {
					{
					{
					setState(25); match(NEWLINE);
					setState(26); stanford();
					}
					}
					setState(31);
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
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FeaturesContext features() {
			return getRuleContext(FeaturesContext.class,0);
		}
		public SheadsContext sheads() {
			return getRuleContext(SheadsContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(DependenciesParser.NUMBER, 0); }
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
			setState(34); id();
			setState(35); match(STRING);
			setState(36); match(STRING);
			setState(37); match(STRING);
			setState(38); features();
			setState(39); match(NUMBER);
			setState(40); match(STRING);
			{
			setState(41); sheads();
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
			setState(43); match(STRING);
			setState(44); match(T__4);
			setState(45); match(STRING);
			setState(46); match(T__5);
			setState(47); match(NUMBER);
			setState(48); match(T__7);
			setState(49); match(STRING);
			setState(50); match(T__5);
			setState(51); match(NUMBER);
			setState(52); match(T__8);
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(DependenciesParser.NUMBER, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitId(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); match(NUMBER);
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
		enterRule(_localctx, 8, RULE_features);
		int _la;
		try {
			setState(65);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(56); match(T__6);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(57); feature();
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(58); match(T__1);
					setState(59); feature();
					}
					}
					setState(64);
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
		enterRule(_localctx, 10, RULE_feature);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); match(STRING);
			setState(68); match(T__2);
			setState(69); match(STRING);
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

	public static class SheadsContext extends ParserRuleContext {
		public SheadContext shead(int i) {
			return getRuleContext(SheadContext.class,i);
		}
		public List<SheadContext> shead() {
			return getRuleContexts(SheadContext.class);
		}
		public SheadsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sheads; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterSheads(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitSheads(this);
		}
	}

	public final SheadsContext sheads() throws RecognitionException {
		SheadsContext _localctx = new SheadsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_sheads);
		int _la;
		try {
			setState(80);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(71); match(T__6);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(72); shead();
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(73); match(T__0);
					setState(74); shead();
					}
					}
					setState(79);
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

	public static class SheadContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(DependenciesParser.STRING, i);
		}
		public TerminalNode NUMBER() { return getToken(DependenciesParser.NUMBER, 0); }
		public List<TerminalNode> STRING() { return getTokens(DependenciesParser.STRING); }
		public SheadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shead; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).enterShead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DependenciesListener ) ((DependenciesListener)listener).exitShead(this);
		}
	}

	public final SheadContext shead() throws RecognitionException {
		SheadContext _localctx = new SheadContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_shead);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); match(NUMBER);
			setState(83); match(T__3);
			setState(84); match(STRING);
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
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\7\2\26"+
		"\n\2\f\2\16\2\31\13\2\3\2\3\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\5\2#\n\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\7\6?\n\6\f\6\16\6B\13\6\5\6D\n\6\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\7\bN\n\b\f\b\16\bQ\13\b\5\bS\n\b\3\t\3\t"+
		"\3\t\3\t\3\t\5\tZ\n\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\2[\2\"\3\2\2\2\4$"+
		"\3\2\2\2\6-\3\2\2\2\b8\3\2\2\2\nC\3\2\2\2\fE\3\2\2\2\16R\3\2\2\2\20T\3"+
		"\2\2\2\22\27\5\4\3\2\23\24\7\16\2\2\24\26\5\4\3\2\25\23\3\2\2\2\26\31"+
		"\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30#\3\2\2\2\31\27\3\2\2\2\32\37\5"+
		"\6\4\2\33\34\7\16\2\2\34\36\5\6\4\2\35\33\3\2\2\2\36!\3\2\2\2\37\35\3"+
		"\2\2\2\37 \3\2\2\2 #\3\2\2\2!\37\3\2\2\2\"\22\3\2\2\2\"\32\3\2\2\2#\3"+
		"\3\2\2\2$%\5\b\5\2%&\7\r\2\2&\'\7\r\2\2\'(\7\r\2\2()\5\n\6\2)*\7\f\2\2"+
		"*+\7\r\2\2+,\5\16\b\2,\5\3\2\2\2-.\7\r\2\2./\7\7\2\2/\60\7\r\2\2\60\61"+
		"\7\6\2\2\61\62\7\f\2\2\62\63\7\4\2\2\63\64\7\r\2\2\64\65\7\6\2\2\65\66"+
		"\7\f\2\2\66\67\7\3\2\2\67\7\3\2\2\289\7\f\2\29\t\3\2\2\2:D\7\5\2\2;@\5"+
		"\f\7\2<=\7\n\2\2=?\5\f\7\2><\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2AD\3"+
		"\2\2\2B@\3\2\2\2C:\3\2\2\2C;\3\2\2\2D\13\3\2\2\2EF\7\r\2\2FG\7\t\2\2G"+
		"H\7\r\2\2H\r\3\2\2\2IS\7\5\2\2JO\5\20\t\2KL\7\13\2\2LN\5\20\t\2MK\3\2"+
		"\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PS\3\2\2\2QO\3\2\2\2RI\3\2\2\2RJ\3\2"+
		"\2\2S\17\3\2\2\2TU\7\f\2\2UV\7\b\2\2VY\7\r\2\2WX\7\t\2\2XZ\7\r\2\2YW\3"+
		"\2\2\2YZ\3\2\2\2Z\21\3\2\2\2\n\27\37\"@CORY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}