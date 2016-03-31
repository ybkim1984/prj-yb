/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repository.accessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.da.file.repository.structure.ColumnValue;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;

public class FileAccessor {
    //
    private static final Charset FILE_ENCODING = Charset.forName("UTF-8");
    private static final int BUFFER_SIZE = 256;
    
    private File dataFile;
    
    //--------------------------------------------------------------------------
    
    public FileAccessor(String dataFilePath) {
        //
        this.dataFile = new File(dataFilePath);
        createDataFile();
    }
    
    //------------------------------------------------------------------------
    
    /**
     * 파일에 데이터 저장(추가)
     * 
     * @param newColumnValues  저장할 컬럼값들
     */
    public void insert(ColumnValueList newColumnValues) {
        //
        BufferedWriter bufferedWriter = null;
        
        try {
            // 파일의 마지막 라인에 데이터 append
            OutputStream os = new FileOutputStream(dataFile, true);
            Writer writer = new OutputStreamWriter(os, FILE_ENCODING);
            bufferedWriter = new BufferedWriter(writer, BUFFER_SIZE);
            
            bufferedWriter.append(newColumnValues.getDataCsv());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 컬럼 조건값들과 모두 일치하는 단일 데이터 조회
     * 
     * @param conditionColumnValues    컬럼 조건값들
     * @return {@link String} 조회 된 CSV 문자열
     */
    public String selectOneCsv(ColumnValueList conditionColumnValues) {
        //
        BufferedReader bufferedReader = null;
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
            
            String csvLine = null;
            
            // 파일의 처음부터 끝까지 순차탐색하며 라인을 읽는다.
            while ((csvLine = bufferedReader.readLine()) != null) {
                //
                String[] datas = csvLine.split(FileConst.SEPARATOR);
                boolean equalValue = true;

                // 검색조건의 값과 모두 일치하면 반환
                for (ColumnValue columnValue : conditionColumnValues.getColumnValues()) {
                    //
                    String targetValue = datas[columnValue.getDataColumnSeq()].trim();
                    
                    if (!targetValue.equals(columnValue.getValue())) {
                        equalValue = false;
                        break;
                    }
                }
                if (equalValue) {
                    return csvLine;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 컬럼 조건값들과 모두 일치하는 데이터 목록 조회
     * 
     * @param conditionColumnValues    컬럼 조건값들
     * @return {@link List}<{@link String}>    조회 된 CSV 문자열 목록
     */
    public List<String> selectListCsv(ColumnValueList conditionColumnValues) {
        //
        BufferedReader bufferedReader = null;
        List<String> csvResults = new ArrayList<String>();
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
            
            String lineStr = null;
            
            // 파일의 처음부터 끝까지 순차탐색하며 라인을 읽는다.
            while ((lineStr = bufferedReader.readLine()) != null) {
                //
                String[] datas = lineStr.split(FileConst.SEPARATOR);
                
                // 모든 컬럼의 값이 조회조건 값과 일치하는지 비교
                if (conditionColumnValues.checkEqualsAllColumnValue(datas)) {
                    //
                    csvResults.add(lineStr);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvResults;
    }
    
    /**
     * 데이터 파일의 모든 CSV 문자열 목록 조회
     * 
     * @return {@link List}<{@link String}>    조회 된 CSV 문자열 목록
     */
    public List<String> selectAll() {
        //
        BufferedReader bufferedReader = null;
        List<String> csvResults = new ArrayList<String>();
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
            
            String line = null;
            
            while ((line = bufferedReader.readLine()) != null) {
                //
                csvResults.add(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvResults;
    }
    
    /**
     * 파일의 데이터 수정
     * <pre>
     * updateColumnValues에서 키컬럼 조건값으로 데이터를 조회하여 
     * updateColumnValues의 모든 컬럼값으로 라인을 덮어쓴다.</pre>
     * 
     * @param updateColumnValues   수정할 컬럼값들 (조회용 키컬럼값 포함)
     */
    public void update(ColumnValueList updateColumnValues) {
        //
        ColumnValue keyColumnValue = updateColumnValues.getKeyColumnValue();
        
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        File newDataFile = null;
        
        try {
            // 수정할 라인을 제외하고 파일 전체를 복사해 새로운 파일을 생성
            newDataFile = File.createTempFile("temp_", ".temp");
            
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
            
            OutputStream os = new FileOutputStream(newDataFile);
            Writer writer = new OutputStreamWriter(os, FILE_ENCODING);
            bufferedWriter = new BufferedWriter(writer, BUFFER_SIZE);
            
            
            String csvLine = null;
            
            while ((csvLine = bufferedReader.readLine()) != null) {
                //
                String[] columnDatas = csvLine.split(FileConst.SEPARATOR);
                
                // 수정 될 라인이면 새로운 데이터를 쓴다.
                if (columnDatas[keyColumnValue.getColumn().getDataColumnSeq()].equals(keyColumnValue.getValue())) {
                    String updateCsv = updateColumnValues.getDataCsv();
                    bufferedWriter.write(updateCsv);
                    continue;
                }
                bufferedWriter.write(csvLine + FileConst.NEW_LINE);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataFile.delete();
            newDataFile.renameTo(dataFile);
        }
    }
    
    
    /**
     * 파일에서 데이터 삭제
     * <pre>모든 조회조건과 일치하는 데이터를 검색하여 삭제</pre>
     * 
     * @param conditionColumnValues    조회조건 컬럼값들
     */
    public void delete(ColumnValueList conditionColumnValues) {
        //
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        
        File newDataFile = null;
        
        try {
            // 삭제할 라인을 제외하고 파일 전체를 복사해 새로운 파일을 생성
            newDataFile = File.createTempFile("temp_", ".temp");
            
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
            
            OutputStream os = new FileOutputStream(newDataFile);
            Writer writer = new OutputStreamWriter(os, FILE_ENCODING);
            bufferedWriter = new BufferedWriter(writer, BUFFER_SIZE);
            
            
            String csvLine = null;
            
            while ((csvLine = bufferedReader.readLine()) != null) {
                //
                String[] columnDatas = csvLine.split(FileConst.SEPARATOR);
                
                // 모든 컬럼의 값이 일치하는지 비교
                if (conditionColumnValues.checkEqualsAllColumnValue(columnDatas)) {
                    //
                    continue;
                }
                bufferedWriter.write(csvLine + FileConst.NEW_LINE);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataFile.delete();
            newDataFile.renameTo(dataFile);
        }
    }
    
    //--------------------------------------------------------------------------
    
    /**
     * 데이터 저장 파일이 없으면 생성
     * 
     */
    private void createDataFile() {
        //
        // 디렉토리가 존재하지 않을 경우 생성
        File dir = new File(FileConst.STORE_DIR);
        
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // 파일이 존재하지 않을 경우 생성
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                System.out.println(dataFile.getName() + " file has been created.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
