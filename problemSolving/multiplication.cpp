#include <iostream>
#include <vector>
using namespace std;

int main()
{
    int n, m;
    cin>>n>>m;
    
    vector<int> first(n);
    vector<int> second(m);
    
    for(int i = 0; i < n; i++){
        cin>>first[i];
    }
    for(int i = 0; i < m; i++){
        cin>>second[i];
    }
    
    
    cout<<endl;
    vector<vector<int> > ans;
    
    for(int i = m - 1; i >= 0; i--){
        int a = second[i];
        vector<int> temp;
        
        int mult = 0;
        int carry = 0;
        for(int j = n - 1; j >= 0; j--){
            int b = first[j];
            if(a * b + carry >= 10){
                mult = (a * b + carry) % 10;
                carry = (a * b + carry) / 10;
                temp.insert(temp.begin(), mult);
            }
            else{
                temp.insert(temp.begin(), a * b + carry);
                carry = 0;
            }
            
        }
        if(carry > 0){
            temp.insert(temp.begin(), carry);
        }
        ans.push_back(temp);
    }
    cout<<endl;
    vector<int> finalAns;
    for(int i = 0; i < ans.size(); i++){
        for(int j = 0; j < ans[i].size(); j++){
            cout<<ans[i][j]<<" ";
        }
        cout<<endl;
    }
    
    int no_of_zeros_added = m - 1;
    
    for(int i = m - 1; i >= 0; i--){
        int back = no_of_zeros_added;
        int front = (m + n) - back - ans[i].size();
        while(back > 0){
            ans[i].push_back(0);
            back--;
        }
        while(front > 0){
            ans[i].insert(ans[i].begin(), 0);
            front--;
        }

        no_of_zeros_added--;

    }

    for(int i = 0; i < ans.size(); i++){
        cout<<ans[i].size()<<" ";
    }
    cout<<endl;
    cout<<endl;
    vector<int> fs;
    int s = 0, c = 0;
    for(int i = (m + n - 1); i >= 0; i--){
        int sum = c;
        for(int j = 0; j < m; j++){
            sum += ans[j][i];
        }
        if(sum >= 10){
           fs.insert(fs.begin(), sum % 10);
           c = sum / 10;
        }
        else{
            fs.insert(fs.begin(), sum);
            c = 0;
        }
    }
    if(c > 0){
        fs.insert(fs.begin(), c);
    }
    
    int res = 0;
    for(int i = 0; i < fs.size(); i++){
        res = res * 10 + fs[i];
    }
    cout<<res<<endl;
    return 0;
}