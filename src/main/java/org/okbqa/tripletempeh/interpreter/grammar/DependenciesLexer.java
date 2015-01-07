// Generated from Dependencies.g4 by ANTLR 4.3
package org.okbqa.tripletempeh.interpreter.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DependenciesLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, STRING=9, 
		NEWLINE=10, WHITESPACE=11;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'"
	};
	public static final String[] ruleNames = {
		"T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "STRING", 
		"NEWLINE", "WHITESPACE"
	};


	public DependenciesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Dependencies.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\r:\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\6\n+\n\n\r\n\16\n,\3\13\5\13\60\n\13\3\13\3\13\3\f\6\f\65\n"+
		"\f\r\f\16\f\66\3\f\3\f\2\2\r\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\3\2\4\f\2##%%)),,..\60\60\62;AAC\\c|\4\2\13\13\"\"<\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\31\3\2"+
		"\2\2\5\33\3\2\2\2\7\35\3\2\2\2\t\37\3\2\2\2\13!\3\2\2\2\r#\3\2\2\2\17"+
		"%\3\2\2\2\21\'\3\2\2\2\23*\3\2\2\2\25/\3\2\2\2\27\64\3\2\2\2\31\32\7+"+
		"\2\2\32\4\3\2\2\2\33\34\7a\2\2\34\6\3\2\2\2\35\36\7/\2\2\36\b\3\2\2\2"+
		"\37 \7*\2\2 \n\3\2\2\2!\"\7<\2\2\"\f\3\2\2\2#$\7?\2\2$\16\3\2\2\2%&\7"+
		"=\2\2&\20\3\2\2\2\'(\7~\2\2(\22\3\2\2\2)+\t\2\2\2*)\3\2\2\2+,\3\2\2\2"+
		",*\3\2\2\2,-\3\2\2\2-\24\3\2\2\2.\60\7\17\2\2/.\3\2\2\2/\60\3\2\2\2\60"+
		"\61\3\2\2\2\61\62\7\f\2\2\62\26\3\2\2\2\63\65\t\3\2\2\64\63\3\2\2\2\65"+
		"\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\b\f\2\29\30\3\2"+
		"\2\2\6\2,/\66\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}