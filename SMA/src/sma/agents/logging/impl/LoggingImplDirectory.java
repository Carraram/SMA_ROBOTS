package sma.agents.logging.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import sma.agents.logging.interfaces.ICreateLogger;
import sma.agents.logging.interfaces.ILog;
import agent.logging.Logging;

public class LoggingImplDirectory extends Logging {

	private final File logDir;

	public LoggingImplDirectory(File logDir) {
		this.logDir = logDir;
		this.logDir.mkdirs();
	}
	
	@Override
	protected Logger make_Logger(String name) {
		return new LoggerImpl(new File(logDir, name+".txt"));
	}
	
	@Override
	protected ICreateLogger make_create() {
		return new ICreateLogger() {
			@Override
			public Logger.Component createStandaloneLogger(String name) {
				return newLogger(name);
			}
		};
	}
	
	private class LoggerImpl extends Logger implements ILog {

		private PrintWriter logWriter;
		
		public LoggerImpl(File logFile) {
			// if an exception happens, nothing can be done about it
			// we just let logStream be null and
			// the operations of logging won't be done
			try {
				this.logWriter = new PrintWriter(new FileWriter(logFile), true);
			} catch (FileNotFoundException e) {
				System.err.println("An error happened with the file, nothing will be logged.");
				this.logWriter = null;
			} catch (IOException e) {
				System.err.println("An error happened with the file, nothing will be logged.");
				this.logWriter = null;
			}
		}
		
		@Override
		protected ILog make_log() {
			return this;
		}

		@Override
		public void addLine(String line) {
			if (logWriter != null) {
				logWriter.println(line);
			}
		}
	}
}