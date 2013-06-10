package com.jcommerce.core.util.dwt;

// $ANTLR 3.2 Sep 23, 2009 12:02:23 Expr.g 2010-02-25 17:32:52


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class Expr extends Lexer {
    public static final int GE=22;
    public static final int LT=19;
    public static final int WHILE=29;
    public static final int OROR=25;
    public static final int VARIABLEACCESS=39;
    public static final int ELSE=28;
    public static final int Rparen=6;
    public static final int SUB=14;
    public static final int INT=31;
    public static final int BANG=23;
    public static final int SEMICOLON=12;
    public static final int Digit=37;
    public static final int EOF=-1;
    public static final int MUL=15;
    public static final int BREAK=30;
    public static final int Identifier=33;
    public static final int IF=27;
    public static final int ANDAND=24;
    public static final int EMPTY=34;
    public static final int LetterOrUnderscore=36;
    public static final int Lbrack=7;
    public static final int Doller=32;
    public static final int WS=42;
    public static final int COMMA=11;
    public static final int Lbrace=9;
    public static final int RealNumber=40;
    public static final int GT=21;
    public static final int EQ=26;
    public static final int DIV=16;
    public static final int Rbrack=8;
    public static final int ISSET=35;
    public static final int Letter=41;
    public static final int Lparen=5;
    public static final int Integer=38;
    public static final int LE=20;
    public static final int STRING=4;
    public static final int EQEQ=17;
    public static final int Rbrace=10;
    public static final int ADD=13;
    public static final int NE=18;

    // delegates
    // delegators

    public Expr() {;} 
    public Expr(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public Expr(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Expr.g"; }

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:3:8: ( '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\"') ) {
                alt3=1;
            }
            else if ( (LA3_0=='\'') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae = new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Expr.g:3:10: '\"' (~ '\"' )* '\"'
                    {
                    match('\"'); 
                    // Expr.g:3:14: (~ '\"' )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // Expr.g:3:15: ~ '\"'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // Expr.g:4:4: '\\'' (~ '\\'' )* '\\''
                    {
                    match('\''); 
                    // Expr.g:4:9: (~ '\\'' )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='\u0000' && LA2_0<='&')||(LA2_0>='(' && LA2_0<='\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // Expr.g:4:10: ~ '\\''
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "Lparen"
    public final void mLparen() throws RecognitionException {
        try {
            int _type = Lparen;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:7:8: ( '(' )
            // Expr.g:7:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Lparen"

    // $ANTLR start "Rparen"
    public final void mRparen() throws RecognitionException {
        try {
            int _type = Rparen;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:10:8: ( ')' )
            // Expr.g:10:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Rparen"

    // $ANTLR start "Lbrack"
    public final void mLbrack() throws RecognitionException {
        try {
            // Expr.g:13:8: ( '[' )
            // Expr.g:13:10: '['
            {
            match('['); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Lbrack"

    // $ANTLR start "Rbrack"
    public final void mRbrack() throws RecognitionException {
        try {
            // Expr.g:16:8: ( ']' )
            // Expr.g:16:10: ']'
            {
            match(']'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Rbrack"

    // $ANTLR start "Lbrace"
    public final void mLbrace() throws RecognitionException {
        try {
            int _type = Lbrace;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:19:8: ( '{' )
            // Expr.g:19:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Lbrace"

    // $ANTLR start "Rbrace"
    public final void mRbrace() throws RecognitionException {
        try {
            int _type = Rbrace;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:22:8: ( '}' )
            // Expr.g:22:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Rbrace"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:25:7: ( ',' )
            // Expr.g:25:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:29:2: ( ';' )
            // Expr.g:29:4: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "ADD"
    public final void mADD() throws RecognitionException {
        try {
            int _type = ADD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:32:5: ( '+' )
            // Expr.g:32:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ADD"

    // $ANTLR start "SUB"
    public final void mSUB() throws RecognitionException {
        try {
            int _type = SUB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:35:5: ( '-' )
            // Expr.g:35:7: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SUB"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            int _type = MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:38:5: ( '*' )
            // Expr.g:38:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:41:5: ( '/' )
            // Expr.g:41:7: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "EQEQ"
    public final void mEQEQ() throws RecognitionException {
        try {
            int _type = EQEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:44:6: ( '==' )
            // Expr.g:44:8: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQEQ"

    // $ANTLR start "NE"
    public final void mNE() throws RecognitionException {
        try {
            int _type = NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:47:4: ( '!=' )
            // Expr.g:47:6: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NE"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:50:4: ( '<' )
            // Expr.g:50:6: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:53:4: ( '<=' )
            // Expr.g:53:6: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:56:4: ( '>' )
            // Expr.g:56:6: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:59:4: ( '>=' )
            // Expr.g:59:6: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GE"

    // $ANTLR start "BANG"
    public final void mBANG() throws RecognitionException {
        try {
            int _type = BANG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:62:6: ( '!' )
            // Expr.g:62:8: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BANG"

    // $ANTLR start "ANDAND"
    public final void mANDAND() throws RecognitionException {
        try {
            int _type = ANDAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:65:8: ( '&&' )
            // Expr.g:65:10: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANDAND"

    // $ANTLR start "OROR"
    public final void mOROR() throws RecognitionException {
        try {
            int _type = OROR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:68:6: ( '||' )
            // Expr.g:68:8: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OROR"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:71:4: ( '=' )
            // Expr.g:71:6: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:74:4: ( 'if' )
            // Expr.g:74:6: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:77:6: ( 'else' )
            // Expr.g:77:8: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:80:7: ( 'while' )
            // Expr.g:80:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "BREAK"
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:83:7: ( 'break' )
            // Expr.g:83:9: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BREAK"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:86:5: ( 'int' )
            // Expr.g:86:7: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "Doller"
    public final void mDoller() throws RecognitionException {
        try {
            // Expr.g:89:8: ( '$' )
            // Expr.g:89:10: '$'
            {
            match('$'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Doller"

    // $ANTLR start "EMPTY"
    public final void mEMPTY() throws RecognitionException {
        try {
            int _type = EMPTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:92:9: ( ( BANG )? 'empty(' Identifier ')' )
            // Expr.g:92:11: ( BANG )? 'empty(' Identifier ')'
            {
            // Expr.g:92:11: ( BANG )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='!') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Expr.g:92:11: BANG
                    {
                    mBANG(); 

                    }
                    break;

            }

            match("empty("); 

            mIdentifier(); 
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EMPTY"

    // $ANTLR start "ISSET"
    public final void mISSET() throws RecognitionException {
        try {
            int _type = ISSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:95:7: ( ( BANG )? 'isset(' Identifier ')' )
            // Expr.g:95:9: ( BANG )? 'isset(' Identifier ')'
            {
            // Expr.g:95:9: ( BANG )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='!') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Expr.g:95:9: BANG
                    {
                    mBANG(); 

                    }
                    break;

            }

            match("isset("); 

            mIdentifier(); 
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ISSET"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:99:2: ( ( Doller )? LetterOrUnderscore ( LetterOrUnderscore | Digit | '.' )* )
            // Expr.g:99:4: ( Doller )? LetterOrUnderscore ( LetterOrUnderscore | Digit | '.' )*
            {
            // Expr.g:99:4: ( Doller )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='$') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Expr.g:99:4: Doller
                    {
                    mDoller(); 

                    }
                    break;

            }

            mLetterOrUnderscore(); 
            // Expr.g:99:31: ( LetterOrUnderscore | Digit | '.' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='.'||(LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Expr.g:
            	    {
            	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "VARIABLEACCESS"
    public final void mVARIABLEACCESS() throws RecognitionException {
        try {
            int _type = VARIABLEACCESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:103:2: ( Identifier ( Lbrack Integer Rbrack )* )
            // Expr.g:103:4: Identifier ( Lbrack Integer Rbrack )*
            {
            mIdentifier(); 
            // Expr.g:103:15: ( Lbrack Integer Rbrack )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='[') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // Expr.g:103:17: Lbrack Integer Rbrack
            	    {
            	    mLbrack(); 
            	    mInteger(); 
            	    mRbrack(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VARIABLEACCESS"

    // $ANTLR start "Integer"
    public final void mInteger() throws RecognitionException {
        try {
            int _type = Integer;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:106:9: ( ( Digit )+ )
            // Expr.g:106:11: ( Digit )+
            {
            // Expr.g:106:11: ( Digit )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // Expr.g:106:11: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee = new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Integer"

    // $ANTLR start "RealNumber"
    public final void mRealNumber() throws RecognitionException {
        try {
            int _type = RealNumber;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:110:2: ( ( Digit )+ '.' ( Digit )+ )
            // Expr.g:110:4: ( Digit )+ '.' ( Digit )+
            {
            // Expr.g:110:4: ( Digit )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Expr.g:110:4: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            match('.'); 
            // Expr.g:110:15: ( Digit )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Expr.g:110:15: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RealNumber"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // Expr.g:114:7: ( '0' .. '9' )
            // Expr.g:114:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Digit"

    // $ANTLR start "LetterOrUnderscore"
    public final void mLetterOrUnderscore() throws RecognitionException {
        try {
            // Expr.g:119:2: ( Letter | '_' )
            // Expr.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LetterOrUnderscore"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // Expr.g:123:8: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
            // Expr.g:123:10: ( 'a' .. 'z' | 'A' .. 'Z' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Expr.g:126:4: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // Expr.g:126:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // Expr.g:126:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\t' && LA12_0<='\n')||LA12_0=='\r'||LA12_0==' ') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // Expr.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // Expr.g:1:8: ( STRING | Lparen | Rparen | Lbrace | Rbrace | COMMA | SEMICOLON | ADD | SUB | MUL | DIV | EQEQ | NE | LT | LE | GT | GE | BANG | ANDAND | OROR | EQ | IF | ELSE | WHILE | BREAK | INT | EMPTY | ISSET | Identifier | VARIABLEACCESS | Integer | RealNumber | WS )
        int alt13=33;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // Expr.g:1:10: STRING
                {
                mSTRING(); 

                }
                break;
            case 2 :
                // Expr.g:1:17: Lparen
                {
                mLparen(); 

                }
                break;
            case 3 :
                // Expr.g:1:24: Rparen
                {
                mRparen(); 

                }
                break;
            case 4 :
                // Expr.g:1:31: Lbrace
                {
                mLbrace(); 

                }
                break;
            case 5 :
                // Expr.g:1:38: Rbrace
                {
                mRbrace(); 

                }
                break;
            case 6 :
                // Expr.g:1:45: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 7 :
                // Expr.g:1:51: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 8 :
                // Expr.g:1:61: ADD
                {
                mADD(); 

                }
                break;
            case 9 :
                // Expr.g:1:65: SUB
                {
                mSUB(); 

                }
                break;
            case 10 :
                // Expr.g:1:69: MUL
                {
                mMUL(); 

                }
                break;
            case 11 :
                // Expr.g:1:73: DIV
                {
                mDIV(); 

                }
                break;
            case 12 :
                // Expr.g:1:77: EQEQ
                {
                mEQEQ(); 

                }
                break;
            case 13 :
                // Expr.g:1:82: NE
                {
                mNE(); 

                }
                break;
            case 14 :
                // Expr.g:1:85: LT
                {
                mLT(); 

                }
                break;
            case 15 :
                // Expr.g:1:88: LE
                {
                mLE(); 

                }
                break;
            case 16 :
                // Expr.g:1:91: GT
                {
                mGT(); 

                }
                break;
            case 17 :
                // Expr.g:1:94: GE
                {
                mGE(); 

                }
                break;
            case 18 :
                // Expr.g:1:97: BANG
                {
                mBANG(); 

                }
                break;
            case 19 :
                // Expr.g:1:102: ANDAND
                {
                mANDAND(); 

                }
                break;
            case 20 :
                // Expr.g:1:109: OROR
                {
                mOROR(); 

                }
                break;
            case 21 :
                // Expr.g:1:114: EQ
                {
                mEQ(); 

                }
                break;
            case 22 :
                // Expr.g:1:117: IF
                {
                mIF(); 

                }
                break;
            case 23 :
                // Expr.g:1:120: ELSE
                {
                mELSE(); 

                }
                break;
            case 24 :
                // Expr.g:1:125: WHILE
                {
                mWHILE(); 

                }
                break;
            case 25 :
                // Expr.g:1:131: BREAK
                {
                mBREAK(); 

                }
                break;
            case 26 :
                // Expr.g:1:137: INT
                {
                mINT(); 

                }
                break;
            case 27 :
                // Expr.g:1:141: EMPTY
                {
                mEMPTY(); 

                }
                break;
            case 28 :
                // Expr.g:1:147: ISSET
                {
                mISSET(); 

                }
                break;
            case 29 :
                // Expr.g:1:153: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 30 :
                // Expr.g:1:164: VARIABLEACCESS
                {
                mVARIABLEACCESS(); 

                }
                break;
            case 31 :
                // Expr.g:1:179: Integer
                {
                mInteger(); 

                }
                break;
            case 32 :
                // Expr.g:1:187: RealNumber
                {
                mRealNumber(); 

                }
                break;
            case 33 :
                // Expr.g:1:198: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\14\uffff\1\33\1\35\1\41\1\43\2\uffff\4\47\1\uffff\1\47\1\56\13"+
        "\uffff\1\60\2\47\1\uffff\1\47\1\uffff\4\47\3\uffff\1\67\5\47\1\uffff"+
        "\1\47\1\76\4\47\1\uffff\1\47\1\102\1\103\2\uffff";
    static final String DFA13_eofS =
        "\104\uffff";
    static final String DFA13_minS =
        "\1\11\13\uffff\4\75\2\uffff\4\56\1\101\2\56\13\uffff\3\56\1\uffff"+
        "\1\56\1\uffff\4\56\3\uffff\6\56\1\uffff\5\56\1\50\1\uffff\1\50\2"+
        "\56\2\uffff";
    static final String DFA13_maxS =
        "\1\175\13\uffff\1\75\1\151\2\75\2\uffff\6\172\1\71\13\uffff\3\172"+
        "\1\uffff\1\172\1\uffff\4\172\3\uffff\6\172\1\uffff\6\172\1\uffff"+
        "\3\172\2\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\4\uffff"+
        "\1\23\1\24\7\uffff\1\41\1\14\1\25\1\15\1\22\1\34\1\33\1\17\1\16"+
        "\1\21\1\20\3\uffff\1\35\1\uffff\1\36\4\uffff\1\37\1\40\1\26\6\uffff"+
        "\1\32\6\uffff\1\27\3\uffff\1\30\1\31";
    static final String DFA13_specialS =
        "\104\uffff}>";
    static final String[] DFA13_transitionS = {
            "\2\31\2\uffff\1\31\22\uffff\1\31\1\15\1\1\1\uffff\1\26\1\uffff"+
            "\1\20\1\1\1\2\1\3\1\12\1\10\1\6\1\11\1\uffff\1\13\12\30\1\uffff"+
            "\1\7\1\16\1\14\1\17\2\uffff\32\27\4\uffff\1\27\1\uffff\1\27"+
            "\1\25\2\27\1\23\3\27\1\22\15\27\1\24\3\27\1\4\1\21\1\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\32",
            "\1\34\47\uffff\1\37\3\uffff\1\36",
            "\1\40",
            "\1\42",
            "",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\5\50\1\44\7\50\1\45\4\50\1\46\7\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\13\50\1\52\1\53\15\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\7\50\1\54\22\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\21\50\1\55\10\50",
            "\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "\1\57\1\uffff\12\30",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\23\50\1\61\6\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\22\50\1\62\7\50",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\22\50\1\63\7\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\17\50\1\64\12\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\10\50\1\65\21\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\4\50\1\66\25\50",
            "",
            "",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\4\50\1\70\25\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\4\50\1\71\25\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\23\50\1\72\6\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\13\50\1\73\16\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\1\74\31\50",
            "",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\23\50\1\75\6\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\30\50\1\77\1\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\4\50\1\100\25\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\12\50\1\101\17\50",
            "\1\36\5\uffff\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff"+
            "\1\50\1\uffff\32\50",
            "",
            "\1\37\5\uffff\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff"+
            "\1\50\1\uffff\32\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "\1\50\1\uffff\12\50\7\uffff\32\50\1\51\3\uffff\1\50\1\uffff"+
            "\32\50",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( STRING | Lparen | Rparen | Lbrace | Rbrace | COMMA | SEMICOLON | ADD | SUB | MUL | DIV | EQEQ | NE | LT | LE | GT | GE | BANG | ANDAND | OROR | EQ | IF | ELSE | WHILE | BREAK | INT | EMPTY | ISSET | Identifier | VARIABLEACCESS | Integer | RealNumber | WS );";
        }
    }
 

}