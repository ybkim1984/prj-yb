/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.accessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.da.file.repo.util.IOUtils;

public class Indexer {
    //
    private File indexFile;

    //--------------------------------------------------------------------------
    
    public Indexer(File indexFile) {
        //
        this.indexFile = indexFile;
    }
    
    //--------------------------------------------------------------------------
    
    /**
     * 인덱스 파일이 없을 경우 새로 생성
     */
    public void createFile() {
        //
        try {
            if (!indexFile.exists()) {
                indexFile.createNewFile();
                System.out.println(indexFile.getName() + " file has been created.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 인덱스 파일을 지우고 새로 생성
     * <pre>인덱스 파일 생성시 데이터 라인을 인덱스 값 기준으로 정렬하여 생성</pre>
     * 
     * @param dataCsvLines  인덱스를 생성할 데이터 CSV 라인 목록
     */
    public void createIndex(List<String> dataCsvLines) {
        //
        // 키(인덱스) 컬럼의 값, 데이터 위치>, 인덱스 값으로 정렬을 해야 하기 때문에 인덱스 값을 map의 키로 잡음
        TreeMap<String, Integer> sortedIndexs = new TreeMap<String, Integer>();
        int currentPosition = 0;
        
        // 각 csv 라인의 위치를 계산하면서 treeMap에 넣어 정렬한다.
        for (String dataCsv : dataCsvLines) {
            //
            String indexValue = dataCsv.split(FileConst.SEPARATOR)[FileConst.INDEX_COLUMN_SEQ];
            
            sortedIndexs.put(indexValue, currentPosition);
            currentPosition += (dataCsv.getBytes().length + FileConst.NEW_LINE_BYTE_LENGTH);
        }

        
        BufferedWriter writer = null;
        
        try {
            OutputStream os = new FileOutputStream(indexFile);
            writer = new BufferedWriter(new OutputStreamWriter(os, FileConst.FILE_ENCODING));
            
            // 정렬된 인덱스 값들을 파일에 쓴다.
            for (Entry<String, Integer> entry : sortedIndexs.entrySet()) {
                //
                String indexValue = entry.getKey();
                int linePosition = entry.getValue();
                
                String writeLine = indexValue + FileConst.SEPARATOR + linePosition + FileConst.NEW_LINE;
                writer.write(writeLine);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
    
    /**
     * 인덱스 조건값에 해당하는 데이터의 데이터 파일에서의 바이트 위치 반환
     * 
     * @param indexColumnValue  조회조건 인덱스 컬럼값
     * @return  {@link Integer} 데이터의 바이트 위치 (검색 된 데이터가 없을 경우 null)
     */
    public Integer findDataPosition(ColumnValue indexColumnValue) {
        //
        BufferedReader reader = null;
        Integer dataPosition = null;
        
        try {
            reader = new BufferedReader(new FileReader(indexFile), FileConst.BUFFER_SIZE);
            String currentLine = null;
            
            // 파일의 처음부터 끝까지 순차탐색
            // 찾으려는 값은 중복이 허용되지 않기 때문에 찾으면 반복문 종료
            while ((currentLine = reader.readLine()) != null) {
                //
                String[] datas = currentLine.split(FileConst.SEPARATOR);
                String targetValue = datas[FileConst.INDEX_COLUMN_SEQ].trim();
                
                if (targetValue.equals(indexColumnValue.getValue())) {
                    dataPosition = Integer.valueOf(datas[datas.length - 1]);
                    break;
                }
                
                // 찾으려는 값이 현재 탐색중인 값보다 작으면 정렬이 된 상태기 때문에 이후에는 값이 없다고 판단
                if (indexColumnValue.getValue().compareTo(targetValue) < 0) {
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return dataPosition;
    }
}
