package com.fair.service;

/**
 * TradeProcessorService interface is a Strategy Pattern
 * For presenting the different dimensions of trade results to UI, mobile app, 
 * you can implement this interface to process the stored trade messages in Database
 */

public interface TradeProcessorService {
    
    public void processTrade();
}