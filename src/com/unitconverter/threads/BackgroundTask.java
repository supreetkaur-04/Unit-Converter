package com.unitconverter.threads;

import javax.swing.*;
import java.awt.BorderLayout;
import com.unitconverter.models.ConversionRequest;
import com.unitconverter.models.ConversionResult;


public class BackgroundTask extends SwingWorker<ConversionResult, Void> {
    private final ConversionRequest request;
    private final ConversionCallback callback;
    
    
    public interface ConversionCallback {
        void onConversionComplete(ConversionResult result);
        void onConversionError(Exception e);
    }
    
    
    public BackgroundTask(ConversionRequest request, ConversionCallback callback) {
        this.request = request;
        this.callback = callback;
    }
    
    
    @Override
    protected ConversionResult doInBackground() throws Exception {
        
        Thread.sleep(100);
        
        long startTime = System.currentTimeMillis();
        
        try {
            
            Class<?> conversionLogicClass = Class.forName("com.unitconverter.ConversionLogic");
            java.lang.reflect.Method convertMethod = conversionLogicClass.getMethod(
                "convert", String.class, String.class, String.class, double.class
            );
            
            double result = (Double) convertMethod.invoke(
                null, 
                request.getCategory(),
                request.getFromUnit(),
                request.getToUnit(),
                request.getInputValue()
            );
            
            long calculationTime = System.currentTimeMillis() - startTime;
            
            return new ConversionResult(request, result, calculationTime);
            
        } catch (Exception e) {
            throw new Exception("Conversion failed: " + e.getMessage(), e);
        }
    }
    
    
    @Override
    protected void done() {
        try {
            ConversionResult result = get();
            if (callback != null) {
                callback.onConversionComplete(result);
            }
        } catch (java.util.concurrent.ExecutionException e) {
            if (callback != null) {
                callback.onConversionError(e.getCause() instanceof Exception ? 
                    (Exception) e.getCause() : new Exception(e.getCause()));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (callback != null) {
                callback.onConversionError(new Exception("Conversion was interrupted", e));
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.onConversionError(e);
            }
        }
    }
    
    
    public int getProgressPercentage() {
        return Math.min(100, Math.max(0, getProgress()));
    }
    
    
    public boolean isRunning() {
        return !isDone() && !isCancelled();
    }
    
    
    public static BackgroundTask executeWithProgress(
            ConversionRequest request, 
            ConversionCallback callback,
            java.awt.Component parentComponent) {
        
        BackgroundTask task = new BackgroundTask(request, callback);
        
        
        JDialog progressDialog = new JDialog(
            parentComponent instanceof JFrame ? (JFrame) parentComponent : null,
            "Converting...", 
            true
        );
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(parentComponent);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Performing conversion...");
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            if (task.cancel(true)) {
                progressDialog.dispose();
            }
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(cancelButton, BorderLayout.SOUTH);
        
        progressDialog.add(panel);
        
        
        task.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setValue(progress);
                progressBar.setString("Converting... " + progress + "%");
            } else if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    progressDialog.dispose();
                }
            }
        });
        
        task.execute();
        
        
        new Thread(() -> {
            try {
                
                for (int i = 0; i <= 100 && !task.isDone(); i += 10) {
                    task.setProgress(i);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        progressDialog.setVisible(true);
        
        return task;
    }
}
