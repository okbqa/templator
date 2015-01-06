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
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		NUMBER=10, STRING=11, NEWLINE=12, WHITESPACE=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'"
	};
	public static final String[] ruleNames = {
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"NUMBER", "STRING", "NEWLINE", "WHITESPACE"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17E\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\6\13\61\n\13\r\13\16\13\62\3\f\6"+
		"\f\66\n\f\r\f\16\f\67\3\r\5\r;\n\r\3\r\3\r\3\16\6\16@\n\16\r\16\16\16"+
		"A\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\3\2\5\3\2\62;\13\2##%%)),,\60\60\62;AAC\\c|\4\2\13\13\"\""+
		"H\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t#\3\2"+
		"\2\2\13%\3\2\2\2\r\'\3\2\2\2\17)\3\2\2\2\21+\3\2\2\2\23-\3\2\2\2\25\60"+
		"\3\2\2\2\27\65\3\2\2\2\31:\3\2\2\2\33?\3\2\2\2\35\36\7+\2\2\36\4\3\2\2"+
		"\2\37 \7.\2\2 \6\3\2\2\2!\"\7a\2\2\"\b\3\2\2\2#$\7/\2\2$\n\3\2\2\2%&\7"+
		"*\2\2&\f\3\2\2\2\'(\7<\2\2(\16\3\2\2\2)*\7?\2\2*\20\3\2\2\2+,\7~\2\2,"+
		"\22\3\2\2\2-.\7=\2\2.\24\3\2\2\2/\61\t\2\2\2\60/\3\2\2\2\61\62\3\2\2\2"+
		"\62\60\3\2\2\2\62\63\3\2\2\2\63\26\3\2\2\2\64\66\t\3\2\2\65\64\3\2\2\2"+
		"\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\30\3\2\2\29;\7\17\2\2:9\3\2"+
		"\2\2:;\3\2\2\2;<\3\2\2\2<=\7\f\2\2=\32\3\2\2\2>@\t\4\2\2?>\3\2\2\2@A\3"+
		"\2\2\2A?\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\b\16\2\2D\34\3\2\2\2\7\2\62\67"+
		":A\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}